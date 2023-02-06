package com.example.skillhack.dao

import android.util.Log
import com.example.skillhack.data.Problem
import com.google.firebase.firestore.FirebaseFirestore

class ProblemsDao {
    private val dataBase = FirebaseFirestore.getInstance()
    private val problemList = dataBase.collection("problems")
    private val _map: HashMap<String, Boolean> = HashMap()
    public val map get()=_map
    fun getProblems(callback:(MutableList<Problem>) -> Unit):List<Problem>{
        var Problems : MutableList<Problem> = mutableListOf()
        Log.d("firebase", "Function Reached")
        problemList.get()
            .addOnSuccessListener { documents->
                Log.e("firebase", "Success entered")
                for(document in documents) {
                    val problem = document.toObject(Problem::class.java)
                    Problems.add(problem)
                }
                callback(Problems)
                Log.e("firebase", Problems.toString())
            }
            .addOnFailureListener { e->
                Log.e("firebase", "Error : $e")
            }
        return Problems
    }
    fun addSkill(skill: String, state: Boolean){
        map.put(skill, state)
    }
    fun getSkills(callback:(List<String>)->Unit):List<String>{
        var Skills : List<String> = mutableListOf()
        dataBase.collection("skills").get()
            .addOnSuccessListener { skills->
                for(skill in skills){
                    Skills.plus(skill)
                }
                callback(Skills)
            }
            .addOnFailureListener { Log.w("TEJAS", "Couldn't fetch list of skills") }

        return Skills
    }


}





