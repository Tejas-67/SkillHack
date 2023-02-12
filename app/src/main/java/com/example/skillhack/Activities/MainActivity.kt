package com.example.skillhack.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.skillhack.R
import com.example.skillhack.dao.ProblemsDao
import com.example.skillhack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private var _binding: ActivityMainBinding?=null
    private val binding get()=_binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.homefcv) as NavHostFragment

        navController=navHostFragment.navController
        setupActionBarWithNavController(navController)

//        val dao = ProblemsDao()
//        dao.getProblems()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.profile){
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()||super.onSupportNavigateUp()
    }
}