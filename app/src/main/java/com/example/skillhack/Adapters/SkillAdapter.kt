package com.example.skillhack.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skillhack.R
import com.example.skillhack.dao.ProblemsDao

class SkillAdapter(val list: List<String>, val pd: ProblemsDao): RecyclerView.Adapter<SkillAdapter.SkillViewHolder>() {


    override fun getItemCount(): Int {
        return list.size
    }
    class SkillViewHolder(val view: View):RecyclerView.ViewHolder(view){
        val skillName=view.findViewById<TextView>(R.id.skilltv)
        val skillcheckbox=view.findViewById<CheckBox>(R.id.skillcheckbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val layout=LayoutInflater.from(parent.context).inflate(R.layout.skill_item, parent, false)
        return SkillViewHolder(layout)
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        holder.skillName.text=list[position]
        holder.skillcheckbox.setOnClickListener{

            if(holder.skillcheckbox.isChecked) pd.addSkill(holder.skillName.text.toString(),true)
            else pd.addSkill(holder.skillName.text.toString(), false)
        }
    }
}