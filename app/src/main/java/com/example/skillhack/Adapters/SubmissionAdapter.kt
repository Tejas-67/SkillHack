package com.example.skillhack.Adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skillhack.R
import kotlinx.coroutines.NonDisposableHandle.parent

class SubmissionAdapter(val list: List<String>):RecyclerView.Adapter<SubmissionAdapter.SubmissionViewHolder>() {
    override fun getItemCount(): Int {
        return list.size
    }
    class SubmissionViewHolder(val view: View):RecyclerView.ViewHolder(view){
        val tv=view.findViewById<TextView>(R.id.submitted_link_tv)

    }
    override fun onBindViewHolder(holder: SubmissionViewHolder, pos: Int) {
        holder.tv.text=list[pos]

        holder.tv.setOnClickListener {
            val q: Uri= Uri.parse(list[pos])
            val intent=Intent(Intent.ACTION_VIEW, q)
            holder.view.context.startActivity(intent)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubmissionViewHolder {
        val layout=LayoutInflater.from(parent.context).inflate(R.layout.submission_item_view, parent, false)
        return SubmissionViewHolder(layout)
    }
}