package com.example.skillhack.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.skillhack.Adapters.AdminProblemAdapter
import com.example.skillhack.Adapters.ProblemAdapter
import com.example.skillhack.LoginActivity
import com.example.skillhack.Models.SharedViewModel
import com.example.skillhack.R
import com.example.skillhack.dao.ProblemsDao
import com.example.skillhack.data.Problem
import com.example.skillhack.databinding.FragmentAdminHomeBinding
import com.example.skillhack.databinding.FragmentHomeBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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
    private var lop: List<Problem> = mutableListOf()
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
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
       _binding=FragmentAdminHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao= ProblemsDao()
        Log.e("adminHomeFragment", "created successfully")
//        binding.problemListRcv.adapter=ProblemAdapter(dao.getProblems())
        binding.addproblemm.setOnClickListener{
            val action=AdminHomeFragmentDirections.actionAdminHomeFragmentToAdminAddProblem()
            binding.root.findNavController().navigate(action)
        }
        dao.getProblems { problems ->
           lop=problems
            binding.adminProblemListRcv.adapter= AdminProblemAdapter(lop, viewModel)
            //implement problem submission list for admin through same rcv
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if(query!=null){
                    val newlist=lop.filter { it.problemname.startsWith(query) }
                    setData(newlist)
                }
                return true

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.admin_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId==R.id.log_out_tv){
            Firebase.auth.signOut()
            startActivity(Intent(this.requireContext(), LoginActivity::class.java))
            activity?.finish()
        }
        return super.onOptionsItemSelected(item)
    }
    fun setData(list : List<Problem>){
        val adapter=AdminProblemAdapter(list, viewModel)
        binding.adminProblemListRcv.adapter=adapter
    }

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