package com.example.skillhack.dao

import android.util.Log
import com.bumptech.glide.load.resource.gif.StreamGifDecoder
import com.example.skillhack.data.Problem
import com.example.skillhack.data.User
import com.example.skillhack.databinding.FragmentLoginPhoneNumberBinding
import com.google.firebase.firestore.FirebaseFirestore
import io.michaelrocks.libphonenumber.android.Phonenumber.PhoneNumber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProblemsDao {
    private val dataBase = FirebaseFirestore.getInstance()
    private val problemList = dataBase.collection("problems")
    private val _map: HashMap<String, Boolean> = HashMap()
    public val map get()=_map
    private val TAG = "Problem Dao"

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
    fun addProblem(problem: Problem)
    {
        problemList.document(problem.pid).set(problem)
            .addOnFailureListener {
                Log.e(TAG, "unable to submit ")
            }
            .addOnSuccessListener {
                Log.d(TAG, "success")
            }
    }

    fun getProblem(problemId :String, callback:(Problem) -> Unit){

        var problem = Problem()
        problemList.document(problemId).get()
            .addOnSuccessListener { u->
                if(u!=null ){
                    problem=u.toObject(Problem::class.java)!!
                    callback(problem)
                }else{
                }
            }
            .addOnFailureListener { e->
                Log.e("TEJAS", "Error : $e")
            }
    }
    suspend fun getSubmissions(uid:String):ArrayList<String> {
        var solvers: ArrayList<String> = arrayListOf()

        dataBase.collection("problems").document(uid).get()
            .addOnSuccessListener { doc ->
                val p = doc.toObject(Problem::class.java)!!
                solvers= p.solvers

                Log.w("tejas", "successsssssssss")

            }
            .addOnFailureListener {
                Log.w("TAG", "Couldnt fetch doc")
            }
            .await()
        Log.w("tejas", "dao me he null ha")
        return solvers
    }
}










