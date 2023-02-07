package com.example.skillhack.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.skillhack.R
import com.example.skillhack.dao.ProblemsDao
import com.example.skillhack.data.Problem
import com.example.skillhack.databinding.FragmentAdminAddProblemBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminAddProblem.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminAddProblem : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentAdminAddProblemBinding?=null
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
        // Inflate the layout for this fragment
        _binding=FragmentAdminAddProblemBinding.inflate(inflater, container, false)
        return binding.root
       // return inflater.inflate(R.layout.fragment_admin_add_problem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var deadline:String=""
        var prize=""
        var desc=""
        var name=""
        var id=""

        binding.addProblemBtn.setOnClickListener {
            if(binding.problemDeadlineEdit.text==null) binding.problemDeadlineInput.error="Enter deadline"
            else deadline= binding.problemDeadlineEdit.text.toString()

            if(binding.problemDescEdit.text==null) binding.problemDescInput.error="Enter description"
            else desc= binding.problemDescEdit.text.toString()

            if(binding.problemStatementEdit.text==null) binding.problemStatementInput.error="Enter deadline"
            else name= binding.problemStatementEdit.text.toString()

            if(binding.problemPrizeEdit.text==null) binding.problemPrizeInput.error="Enter deadline"
            else deadline= binding.problemPrizeEdit.text.toString()

            if(binding.problemPidEdit.text==null) binding.problemPidInput.error="Enter deadline"
            else id= binding.problemPidEdit.text.toString()



            val newproblem=Problem()
            newproblem.prize=prize.toInt()
            newproblem.deadline=deadline
            newproblem.problemdesc=desc
            newproblem.problemname=name

            newproblem.pid=id

            val problemDao= ProblemsDao()
            problemDao.addProblem(newproblem)
            setNull()
        }
    }
    fun setNull(){
        binding.problemPidEdit.text=null
        binding.problemPidInput.error=null
        binding.problemDeadlineEdit.text=null
        binding.problemDeadlineInput.error=null
        binding.problemSkillsEdit.text=null
        binding.problemSkillsInput.error=null
        binding.problemDescEdit.text=null
        binding.problemDescInput.error=null
        binding.problemStatementEdit.text=null
        binding.problemStatementInput.error=null
        binding.problemPrizeEdit.text=null
        binding.problemPrizeInput.error=null

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminAddProblem.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminAddProblem().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}