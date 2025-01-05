package com.example.ebay_clone.ui.myebay

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ebay_clone.databinding.ActivityLogUpBinding
import com.google.firebase.auth.FirebaseAuth

class LogUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogUpBinding.inflate(layoutInflater)
        setContentView(binding.root) // Set up the view with View Binding

        firebaseAuth = FirebaseAuth.getInstance()

        // Cancel button to finish the activity
        binding.CancelLogup.setOnClickListener {
            finish()
        }

        // Sign up button click handler
        binding.buttonSignUp.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            // Validate fields
            if (email.isEmpty()) {
                binding.editTextEmail.error = "Email cannot be empty"
                return@setOnClickListener
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.editTextEmail.error = "Invalid email format"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.editTextPassword.error = "Password cannot be empty"
                return@setOnClickListener
            }
            if (password.length < 6) {
                binding.editTextPassword.error = "Password must be at least 6 characters"
                return@setOnClickListener
            }

            // Firebase authentication
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LogInActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val errorMessage = task.exception?.message ?: "Registration failed"
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Already have an account, navigate to login
        binding.linearLayoutCreateAccount.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
