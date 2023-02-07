package com.example.skillhack.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.skillhack.Activities.MainActivity
import com.example.skillhack.Adapters.SkillAdapter
import com.example.skillhack.R
import com.example.skillhack.dao.ProblemsDao
import com.example.skillhack.dao.UserDao
import com.example.skillhack.databinding.FragmentProfileSetupSkillBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ProfileSetupSkillFragment : Fragment() {

    private lateinit var dob: String
    private lateinit var name: String
    private lateinit var phonenumber: String
    private lateinit var auth:FirebaseAuth
    private var isSuccessfulSetup = false
    private var _binding: FragmentProfileSetupSkillBinding?=null
    private val binding get()=_binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name=it.getString("name")!!
            dob=it.getString("dateofbirth")!!
            phonenumber=it.getString("phonenumber")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth= FirebaseAuth.getInstance()
        _binding= FragmentProfileSetupSkillBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            val pd=ProblemsDao()

        GlobalScope.launch {

            pd.getSkills { skills ->
                Log.e("setup skill", "skills---------->${skills}")
                binding.skillPageRCV.adapter = SkillAdapter(skills, pd)
                Toast.makeText(requireContext(), "Select At most 6 skills", Toast.LENGTH_LONG)
                    .show()
            }

        }
        binding.skillPageSubmitBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            GlobalScope.launch(Dispatchers.Main) {

                isSuccessfulSetup = true
                val skill= mutableListOf<String>()
                val map : HashMap<String, Boolean> = pd.map
                for ((key, value) in map) {
                    if(value) skill.add(key)
                }

                //add user to database
                val userDao = UserDao()
                userDao.addUserSkills(phonenumber, name , dob, skill)
                sendToMain()
            }



        }


    }
    private fun sendToMain() {

        startActivity(Intent(this.requireContext(), MainActivity::class.java))

        //activity?.finish()
    }
    override fun onDestroy() {
        super.onDestroy()
//        if(!isSuccessfulSetup) auth.signOut()
       // activity?.finish()
    }
}
//var problemCount :Int=0,
//var image: String = "",
//var name: String ="",
//var problems: ArrayList<Map<String,String>> = arrayListOf(mapOf()),
//var rewardsEarned: Int = 0,
//var skills :ArrayList<String> = arrayListOf(),
//var mobileNumber : String = "",
//var dob: String = ""