package com.example.skillhack.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.skillhack.Models.SharedViewModel
import com.example.skillhack.dao.ProblemsDao
import com.example.skillhack.dao.UserDao
import com.example.skillhack.data.Problem
import com.example.skillhack.data.User
import com.example.skillhack.databinding.FragmentProblemDescripitonBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProblemDescripitonFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProblemDescripitonFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var p: Problem
    private var pname: String? = null
    private var pdesc: String? = null
    private var deadline: String? = null
    private var prize: Int? = null
    private var problemId : String? = null

    private var phoneNumber = Firebase.auth.currentUser!!.phoneNumber!!

  //  private val viewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentProblemDescripitonBinding?=null
    private val binding get()=_binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
           pname=it.getString("problemname")
            pdesc=it.getString("problemdesc")
            deadline=it.getString("deadline")
            prize=it.getInt("prize")
            problemId = it.getString("problemId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       _binding=FragmentProblemDescripitonBinding.inflate(inflater, container , false)

        binding.driveLinkInputTexteditlayout.visibility=View.INVISIBLE
        binding.driveLinkSubmitButton.visibility=View.INVISIBLE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.driveLinkSubmitButton.visibility=View.GONE
        binding.driveLinkInputTexteditlayout.visibility=View.GONE
        binding.startSolvingBtn.setOnClickListener {
            binding.startSolvingBtn.visibility=View.GONE
            binding.driveLinkSubmitButton.visibility=View.VISIBLE
            binding.driveLinkInputTexteditlayout.visibility=View.VISIBLE
        }
        binding.driveLinkSubmitButton.setOnClickListener {
//            binding.ProgressBar.visibility = View.VISIBLE
            val solution = binding.driveLinkInputTexteditlayout.text.toString()
            if(("https://drive.google.com/" in solution) or ("https://github.com/" in solution))
            {
                binding.driveLinkInputTexteditlayout.visibility = View.GONE
                binding.driveLinkInputTextinputlayout.error = null
                GlobalScope.launch(Dispatchers.Main) {
                    UserDao().getUser(phoneNumber){ user->
                            user.problems.add(problemId!!)
                            user.problemCount += 1
                            UserDao().addUser(user)

                            ProblemsDao().getProblem(problemId!!){ problem ->
                                problem.solvers.add(user.name)
                                Log.e("problemDesc", "  inside....")
                                val mutableMap = problem.submissions.toMutableMap()
                                mutableMap[user.name] = solution
                                problem.submissions = mutableMap.toMap()
                                Log.e("problemDesc", "  inside....\n username#${user.name} submissions $${problem.submissions}")

                                ProblemsDao().addProblem(problem)
                            }
                        }
                    Toast.makeText(context,"Submitted successfully",Toast.LENGTH_LONG).show()
                }
            }
            else
            {
                binding.driveLinkInputTextinputlayout.error = "Invalid Link"
            }


        }
        binding.startSolvingBtn.setOnClickListener{
            binding.startSolvingBtn.visibility=View.INVISIBLE
            binding.driveLinkSubmitButton.visibility=View.VISIBLE
            binding.driveLinkInputTexteditlayout.visibility=View.VISIBLE
        }
//        binding.driveLinkSubmitButton.
        binding.problemDescriptionTv.text=pdesc
        binding.problemStatementTv.text=pname
        binding.deadlineTv.text=deadline
        binding.cashPrizeTv.text=prize.toString()

    }



    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProblemDescripitonFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}