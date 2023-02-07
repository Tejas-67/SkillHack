package com.example.skillhack.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.skillhack.LoginActivity
import com.example.skillhack.R
import com.example.skillhack.dao.UserDao
import com.example.skillhack.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var auth:FirebaseAuth
    private var _binding: FragmentProfileBinding?=null
    private val binding get()=_binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profilesectionSV.visibility=View.GONE
        auth=FirebaseAuth.getInstance()
        val userDao= UserDao()

        if(auth.currentUser == null) Log.e("ProfileFragment", ".......auth.currentUser is null")
        userDao.getUser(auth.currentUser!!.phoneNumber!!){ user->
            binding.profilepageprogressbar.visibility=View.GONE
            binding.profilesectionSV.visibility=View.VISIBLE

            binding.userNameTv.text=user.name
            binding.phoneNumberTv.text=user.mobileNumber
            binding.profilePageRewardsearnedtv.text=user.rewardsEarned.toString()
            binding.profilePageProblemsolvedtv.text=user.problemCount.toString()

            Glide.with(requireContext()).load(user.image).into(binding.userImageview)
        }
        binding.logOutTv.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this.requireContext(),LoginActivity::class.java))
            activity?.finish()
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}