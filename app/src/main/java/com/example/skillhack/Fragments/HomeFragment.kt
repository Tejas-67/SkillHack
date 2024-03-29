package com.example.skillhack.Fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.skillhack.Adapters.ProblemAdapter
import com.example.skillhack.Models.SharedViewModel
import com.example.skillhack.R
import com.example.skillhack.dao.ProblemsDao
import com.example.skillhack.data.Problem
import com.example.skillhack.databinding.FragmentHomeBinding
import com.google.common.collect.Iterables
import com.google.firebase.firestore.DocumentReference

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentHomeBinding?=null
    private val binding get()=_binding!!
    private var lop: List<Problem> = mutableListOf()

    private lateinit var adapter: ProblemAdapter
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
        _binding=FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao=ProblemsDao()
//        binding.problemListRcv.adapter=ProblemAdapter(dao.getProblems())

        dao.getProblems { problems ->
            lop=problems
            binding.problemListRcv.adapter=ProblemAdapter(lop)
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
        //binding.problemListRcv.adapter=ProblemAdapter(lop)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
    }

    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu, menu)



//        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//
//                return false
//            }
//            override fun onQueryTextChange(query: String?): Boolean {
//                if(query!=null){
//                    val newlist=lop.filter { it.problemname.startsWith(query) }
//                    setData(newlist)
//                }
//                return true
//
//            }
//        })



    }

    fun setData(list : List<Problem>){
        adapter=ProblemAdapter(list)
        binding.problemListRcv.adapter=adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if(item.itemId==R.id.profile){
            val action=HomeFragmentDirections.actionHomeFragmentToProfileFragment()
            binding.root.findNavController().navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroy() {
        super.onDestroy()
        activity?.finish()
    }
}