package com.example.ebay_clone.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.ebay_clone.R
import com.example.ebay_clone.ui.myebay.LogInActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Apply fade-in animation to the logo
        val logo = findViewById<ImageView>(R.id.logo) // Ensure your logo has this ID in XML
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        logo.startAnimation(fadeInAnimation)

        // Delay for 3 seconds and navigate to LogInActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out) // Apply transition animations
            finish() // Finish SplashScreenActivity to remove it from the back stack
        }, 3000) // 3000 milliseconds = 3 seconds
    }
}
