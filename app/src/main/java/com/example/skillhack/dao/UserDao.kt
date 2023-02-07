package com.example.skillhack.dao

import android.content.Context
import android.util.Log
import androidx.navigation.findNavController
import com.example.skillhack.Fragments.ProfileSetupFragmentDirections
import com.example.skillhack.Fragments.ProfileSetupSkillFragment
import com.example.skillhack.data.Problem
import com.example.skillhack.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserDao {
    private val TAG = "UserDao"
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    suspend fun checkNumberAlreadyExists( phoneNumber:String) : Boolean
    {

        var flag =false
        db.collection("users").document(phoneNumber).get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful && task.result != null && task.result.exists()){
                    flag = true
                }
            }
            .await()

        return flag

    }
    fun addUserSkills(phoneNumber: String, name: String, dob: String, skills:MutableList<String>)
    {
        val user = User()
        user.name = name
        user.dob = dob
        user.mobileNumber = phoneNumber
        if(skills.isEmpty()) Log.e(TAG, "in addUserSkills skills is getting null")
        user.skills = ArrayList(skills)

        db.collection("users").document(phoneNumber).set(user)
            .addOnSuccessListener {
                Log.e(TAG, "skills added")
            }
            .addOnFailureListener {
                Log.e(TAG, "Internet connection problem")
            }
    }
    fun getUser(phoneNumber:String, callback:(User) -> Unit){
        //val phoneNumber=auth.currentUser!!.phoneNumber!!
        var user: User=User()
        val userList=db.collection("users")
        Log.d("TEJAS", "Function Reached till getUser")
        userList.document(phoneNumber).get()
            .addOnSuccessListener { u->
                if(u!=null){
                    Log.w("TEJAS", "User Found")
                    user=u.toObject(User::class.java)!!
                    callback(user)
                }else{
                    Log.w("TEJAS", "No such user")
                }
            }
            .addOnFailureListener { e->
                Log.e("TEJAS", "Error : $e")
            }
       // return user
    }
}