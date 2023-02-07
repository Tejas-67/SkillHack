package com.example.skillhack.dao

import android.util.Log
import com.example.skillhack.data.Problem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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

    fun getSkills(callback:(List<String>)->Unit): List<String> {


            var Skills : MutableList<String> = mutableListOf()
            dataBase.collection("allSkills").get()
                .addOnSuccessListener { skills->
                    for(skill in skills){
                        Skills.add(skill.id)
                        Log.e("TAG", "skill.id =-->${skill.id}")
                    }
                    callback(Skills)
                }
                .addOnFailureListener {
                    Log.e("TAG", "failed to retrieve all skills from database")
                }


            Log.e("TAG","..............skills --->>$Skills")

        return Skills
    }

    suspend fun getSubmissions(uid:String):List<String> {
        var submissions: ArrayList<String> = ArrayList()

        dataBase.collection("problems").document(uid).get()
            .addOnSuccessListener { doc ->
                val p = doc.toObject(Problem::class.java)!!
                submissions = p.submissions

                Log.w("tejas", "successsssssssss")

            }
            .addOnFailureListener {
                Log.w("TAG", "Couldnt fetch doc")
            }
            .await()
        Log.w("tejas", "dao me he null ha")
        return submissions
    }


}





