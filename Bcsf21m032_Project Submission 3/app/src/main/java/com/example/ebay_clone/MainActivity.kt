package com.example.ebay_clone

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.ebay_clone.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Draw content behind the status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
        window.statusBarColor = Color.TRANSPARENT

        // Set up navigation view
        val navView: BottomNavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        // Handle bottom navigation item selection
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.navigation_home)
                    true
                }
                R.id.navigation_myebay -> {
                    // Navigate to My eBay fragment if it's not the current destination
                    if (navController.currentDestination?.id != R.id.navigation_myebay) {
                        navController.navigate(R.id.navigation_myebay)
                    }
                    true
                }
                R.id.navigation_search -> {
                    navController.navigate(R.id.navigation_search)
                    true
                }
                R.id.navigation_notifications -> {
                    navController.navigate(R.id.navigation_notifications)
                    true
                }
                R.id.navigation_selling -> {
                    navController.navigate(R.id.navigation_selling)
                    true
                }
                else -> false
            }
        }
    }
}
