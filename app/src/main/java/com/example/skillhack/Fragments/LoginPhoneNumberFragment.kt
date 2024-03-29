package com.example.skillhack.Fragments

import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.skillhack.Activities.AdminActivity
import com.example.skillhack.Activities.MainActivity
import com.example.skillhack.dao.UserDao
import com.example.skillhack.databinding.FragmentLoginPhoneNumberBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit


class LoginPhoneNumberFragment : Fragment() {
    val TAG = "Login Phone Tag"
    private var _binding : FragmentLoginPhoneNumberBinding? = null
    private val binding get() =_binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var countryCodePicker : CountryCodePicker
    private lateinit var phoneNumber: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginPhoneNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        auth = Firebase.auth
        phoneNumberSignIn()
//        binding.button2.setOnClickListener{
//            val action = LoginPhoneNumberFragmentDirections.actionLoginPhoneNumberFragment2ToLoginEmailFragment()
//            binding.root.findNavController().navigate(action)
//        }
    }

    private fun phoneNumberSignIn() {
        binding.button.setOnClickListener {
           // if(binding.phoneNumber.text.toString()=="+919775683700") sendToMain()
            val ccp = binding.countryCodeSelector
            //adding phoneNumber to country code. Now "+91" becomes "+91 9876543210"
            ccp.registerPhoneNumberTextView(binding.phoneNumber)
            phoneNumber =  ccp.fullNumberWithPlus.replace(" ","")
            Log.e(TAG, phoneNumber)

            if (phoneNumber.length<5) return@setOnClickListener
            binding.getOtpProgressBar.visibility = View.VISIBLE
            initiateOtp(phoneNumber)

        }
    }
    private fun initiateOtp(phoneNumber :String) {

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this.requireActivity())                 // Activity (for callback binding)
            .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    private val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.e(TAG, "onVerificationCompleted:$credential")
            binding.getOtpProgressBar.visibility= View.INVISIBLE
//             signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.e(TAG, "onVerificationFailed", e)
            binding.getOtpProgressBar.visibility= View.INVISIBLE

            if (e is FirebaseAuthInvalidCredentialsException) {
                Log.e(TAG, "acha phone number galat he")
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {

//            GlobalScope.launch {
//                withContext(Dispatchers.IO) {
                    Log.e(TAG, "onCodeSent :$verificationId")

                val action =
                    LoginPhoneNumberFragmentDirections.actionLoginPhoneNumberFragmentToOtpVerificationFragment(
                        otp=verificationId, token, phoneNumber=phoneNumber
                    )
                binding.getOtpProgressBar.visibility = View.INVISIBLE
                binding.root.findNavController().navigate(action)


        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this.requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.e(TAG, "signInWithCredential:success")


                    Toast.makeText(requireContext(), "Authenticated Successfully! ", Toast.LENGTH_LONG).show()
                    updateUI()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.e(TAG, "signInWithPhoneAuthCredential:failure :${task.exception.toString()}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }


    private fun updateUI() {
        if(auth.currentUser!!.phoneNumber == "+919876543210")
            sendToAdminActivity()
        else {
            GlobalScope.launch(Dispatchers.Main) {
                Log.e(TAG, "...............ck1.............")
                val userDao = UserDao()
                if (userDao.checkNumberAlreadyExists(auth.currentUser!!.phoneNumber!!)) {
                    Log.e(TAG, "Account already created.....")
                    sendToMain()
                } else {
                    Log.e(TAG, "Account not created.....")
                    val action =
                        LoginPhoneNumberFragmentDirections.actionLoginPhoneNumberFragmentToProfileSetupFragment(phoneNumber)
                    findNavController().navigate(action)

                    Log.e(TAG, "...............ck3.............")
                }
            }
        }
    }
    private fun sendToMain() {
        startActivity(Intent(this.requireContext(), MainActivity::class.java))
        activity?.finish()
    }
    private fun sendToAdminActivity() {
        startActivity(Intent(this.requireContext(), AdminActivity::class.java))
        requireActivity().finish()
    }
    override fun onStart() {
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            if(auth.currentUser!!.phoneNumber == "+919876543210")
                sendToAdminActivity()
            else
                sendToMain()
        }
        super.onStart()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
