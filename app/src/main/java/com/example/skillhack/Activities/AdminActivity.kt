package com.example.skillhack.Activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.skillhack.R
import com.example.skillhack.databinding.ActivityAdminBinding
import com.example.skillhack.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class AdminActivity: AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityAdminBinding
    //private val binding get()=_binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w("TEJAS", "ADmin reached")
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.admin_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}