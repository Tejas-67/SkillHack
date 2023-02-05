package com.example.skillhack.dao

import android.util.Log
import com.example.skillhack.data.Problem
import com.google.firebase.firestore.FirebaseFirestore

class ProblemsDao {
    private val dataBase = FirebaseFirestore.getInstance()
    private val problemList = dataBase.collection("problems")

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


}





