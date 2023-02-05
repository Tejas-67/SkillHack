package com.example.skillhack.dao

import android.util.Log
import com.example.skillhack.data.problem
import com.google.firebase.firestore.FirebaseFirestore

class ProblemsDao {
    private val dataBase = FirebaseFirestore.getInstance()
    private val problemList = dataBase.collection("problems")

    fun getProblems(callback:(MutableList<problem>) -> Unit):List<problem>{
        var problems : MutableList<problem> = mutableListOf()
        Log.d("firebase", "Function Reached")
        problemList.get()
            .addOnSuccessListener { documents->
                Log.e("firebase", "Success entered")
                for(document in documents) {
                    val problem = document.toObject(problem::class.java)
                    problems.add(problem)
                }
                callback(problems)
                Log.e("firebase", problems.toString())
            }
            .addOnFailureListener { e->
                Log.e("firebase", "Error : $e")
            }
        return problems
    }


}





