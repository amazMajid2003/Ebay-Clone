package com.example.ebay_clone.ui.myebay

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ebay_clone.R

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)  // Inflate the XML layout

        // Find Button 1 by its ID
        val button1: Button = findViewById(R.id.button1)

        // Set OnClickListener for Button 1
        button1.setOnClickListener {
            // Create an Intent to navigate to LogInActivity
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent) // Start the new activity
        }
        // Find Button 4 by its ID
        val button4: Button = findViewById(R.id.button4)

        // Set OnClickListener for Button 4
        button4.setOnClickListener {
            // Create an Intent to navigate to the desired activity
            val intent = Intent(this, SignUpActivity::class.java) // Replace with actual activity class
            startActivity(intent) // Start the new activity
        }
    }
}
