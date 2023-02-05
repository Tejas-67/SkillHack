package com.example.skillhack.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.skillhack.Adapters.ProblemAdapter
import com.example.skillhack.R
import com.example.skillhack.dao.ProblemsDao
import com.example.skillhack.data.problem
import com.example.skillhack.databinding.FragmentHomeBinding
import com.google.common.collect.Iterables
import com.google.firebase.firestore.DocumentReference

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentHomeBinding?=null
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
        _binding=FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao=ProblemsDao()
//        binding.problemListRcv.adapter=ProblemAdapter(dao.getProblems())

        dao.getProblems { problems ->
            binding.problemListRcv.adapter = ProblemAdapter(problems)
            Toast.makeText(requireContext(), "Halwa", Toast.LENGTH_SHORT).show()
        }


        binding.button.setOnClickListener {
            val s="Working"
            val action=HomeFragmentDirections.actionHomeFragmentToProblemDescripitonFragment(s)
            binding.root.findNavController().navigate(action)
        }
    }
}