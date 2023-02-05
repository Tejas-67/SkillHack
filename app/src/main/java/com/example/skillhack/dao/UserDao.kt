package com.example.skillhack.dao

import android.util.Log
import androidx.navigation.findNavController
import com.example.skillhack.Fragments.ProfileSetupFragmentDirections
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserDao {
    private val TAG = "UserDao"
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
}