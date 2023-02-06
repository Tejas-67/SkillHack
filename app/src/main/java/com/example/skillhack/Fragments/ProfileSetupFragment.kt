package com.example.skillhack.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.skillhack.R
import com.example.skillhack.dao.UserDao
import com.example.skillhack.data.User
import com.example.skillhack.databinding.FragmentHomeBinding
import com.example.skillhack.databinding.FragmentProfileSetupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*


class ProfileSetupFragment : Fragment() {
    private val TAG = "ProfileSetup Fragment"
    private var _binding :FragmentProfileSetupBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding= FragmentProfileSetupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val datePicker = binding.datePicker1
        val today = Calendar.getInstance()
        auth = Firebase.auth
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            val month = month + 1
//            val msg = "You Selected: $day/$month/$year"
//            Toast.makeText(this.requireContext(), msg, Toast.LENGTH_SHORT).show()
        }
        binding.submitButton.setOnClickListener{

            val user = User(0,"",binding.userNameEditText.text.toString(), arrayListOf(mapOf()), 0, arrayListOf(),auth.currentUser!!.phoneNumber!!,datePicker.dayOfMonth.toString()+"-"+datePicker.month.toString()+"-"+datePicker.year.toString())
            db.collection("users").document(user.mobileNumber).set(user)
                .addOnSuccessListener {
                    val action =ProfileSetupFragmentDirections.actionProfileSetupFragmentToProfileSetupSkillFragment()
                    binding.root.findNavController().navigate(action)
                }
                .addOnFailureListener {
                    Toast.makeText(this.requireContext(), "Check your network connection", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        auth.signOut()
        activity?.finish()
    }


}