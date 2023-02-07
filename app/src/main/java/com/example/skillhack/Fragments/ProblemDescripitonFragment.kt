package com.example.skillhack.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.skillhack.Models.SharedViewModel
import com.example.skillhack.data.Problem
import com.example.skillhack.databinding.FragmentProblemDescripitonBinding

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
    private var param2: String? = null

    private val viewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentProblemDescripitonBinding?=null
    private val binding get()=_binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param2 = it.getString(ARG_PARAM2)
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
       // binding.driveLinkSubmitButton.setOnClickListener { Toast.makeText(requireContext(), "Working fine $uid", Toast.LENGTH_SHORT).show() }
        binding.startSolvingBtn.setOnClickListener{
            binding.startSolvingBtn.visibility=View.INVISIBLE
            binding.driveLinkSubmitButton.visibility=View.VISIBLE
            binding.driveLinkInputTexteditlayout.visibility=View.VISIBLE
        }
        binding.problemDescriptionTv.text=viewModel.currproblem?.problemdesc
        binding.problemStatementTv.text= viewModel.currproblem?.problemname
        binding.deadlineTv.text=viewModel.currproblem?.deadline
        binding.cashPrizeTv.text=viewModel.currproblem?.prize.toString()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.nullCurrProb()
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