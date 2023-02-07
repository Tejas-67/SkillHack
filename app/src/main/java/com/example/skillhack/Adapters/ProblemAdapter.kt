package com.example.skillhack.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.skillhack.Fragments.HomeFragmentDirections
import com.example.skillhack.Models.SharedViewModel
import com.example.skillhack.R
import com.example.skillhack.data.Problem

class ProblemAdapter(val list: List<Problem>): RecyclerView.Adapter<ProblemAdapter.ProblemViewHolder>() {

    override fun getItemCount(): Int {
        return list.size
    }
    class ProblemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
       val problemName=view.findViewById<TextView>(R.id.cardview_problem_name)
        val problemDesc=view.findViewById<TextView>(R.id.cardview_problem_desc)
        val deadline=view.findViewById<TextView>(R.id.deadline_tv)
        val prize=view.findViewById<TextView>(R.id.cash_prize_tv)
        val viewMoreBtn=view.findViewById<TextView>(R.id.view_more_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemViewHolder {
        val layout=LayoutInflater.from(parent.context).inflate(R.layout.problem_item_view, parent , false)
        return ProblemViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ProblemViewHolder, position: Int) {
        val p:Problem=list[position]
        holder.problemName.text=p.problemname.toString()
        holder.problemDesc.text=p.problemdesc
        holder.deadline.text=p.deadline.toString()
        holder.prize.text=p.prize.toString()

        holder.viewMoreBtn.setOnClickListener {

            val action=HomeFragmentDirections.actionHomeFragmentToProblemDescripitonFragment(problemname = p.problemname, problemdesc = p.problemdesc, deadline = p.deadline, prize = p.prize, problemId = p.pid
            )

            holder.view.findNavController().navigate(action)

        }
    }

}


