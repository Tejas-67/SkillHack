package com.example.skillhack.Fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.skillhack.Adapters.AdminProblemAdapter
import com.example.skillhack.Adapters.ProblemAdapter
import com.example.skillhack.Models.SharedViewModel
import com.example.skillhack.R
import com.example.skillhack.dao.ProblemsDao
import com.example.skillhack.databinding.FragmentAdminHomeBinding
import com.example.skillhack.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminHomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val viewModel : SharedViewModel by activityViewModels()
    private var _binding:FragmentAdminHomeBinding?=null
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
       _binding=FragmentAdminHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao= ProblemsDao()
        //Log.w("TEJAS", "$p")
//        binding.problemListRcv.adapter=ProblemAdapter(dao.getProblems())
        binding.addproblemm.setOnClickListener{
            val action=AdminHomeFragmentDirections.actionAdminHomeFragmentToAdminAddProblem()
            binding.root.findNavController().navigate(action)
        }
        dao.getProblems { problems ->
           binding.adminProblemListRcv.adapter= AdminProblemAdapter(problems, viewModel)
            //implement problem submission list for admin through same rcv
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater?.inflate(R.menu.admin_menu, menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        if(item.itemId==R.id.addproblem){
//            val action=AdminHomeFragmentDirections.actionAdminHomeFragmentToAdminAddProblem()
//            binding.root.findNavController().navigate(action)
//        }
//        return super.onOptionsItemSelected(item)
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminHomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}