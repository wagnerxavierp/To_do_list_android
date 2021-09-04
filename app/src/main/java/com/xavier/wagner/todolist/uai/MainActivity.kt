package com.xavier.wagner.todolist.uai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.xavier.wagner.todolist.R
import com.xavier.wagner.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
        initNavController()
    }

    private fun initNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setupBottomNavigation(){
        binding.bottomNavigation.setOnNavigationItemSelectedListener  { item ->

            val currentDestination =  binding.navHostFragment.findNavController().currentDestination?.id

            when(item.itemId) {
                R.id.homeNavigation -> {
                    if(currentDestination != R.id.homeFragment)
                        binding.navHostFragment.findNavController()
                            .navigate(R.id.homeFragment)
                    true
                }
                R.id.accountNavigation -> {
                    if(currentDestination != R.id.accountFragment)
                        binding.navHostFragment.findNavController()
                            .navigate(R.id.accountFragment)
                    true
                }else -> {
                    false
                }
            }
        }
    }

    override fun onBackPressed() {
        val currentDestination =  binding.navHostFragment.findNavController().currentDestination?.id
        if (currentDestination != R.id.homeFragment) {
            binding.bottomNavigation.selectedItemId = R.id.homeNavigation
        }
        else {
            finish()
        }
    }
}