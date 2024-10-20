package com.example.ebay_clone.ui.myebay

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.ebay_clone.R

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)  // Inflate the XML layout

        // Find the button by its ID
        val buttonContinueWithEmail: Button = findViewById(R.id.buttonContinueWithEmail)

        // Set OnClickListener for the button
        buttonContinueWithEmail.setOnClickListener {
            // Create an Intent to navigate to the LogUpActivity
            val intent = Intent(this, LogUpActivity::class.java) // Replace with actual activity class
            startActivity(intent) // Start the new activity
        }
    }
}
