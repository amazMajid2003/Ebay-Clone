package com.example.ebay_clone.ui.myebay

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ebay_clone.MainActivity
import com.example.ebay_clone.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        // Close activity when search icon is clicked
        binding.imageViewSearch.setOnClickListener {
            finish()
        }

        // Handle sign-in button click
        binding.buttonSignIn.setOnClickListener {
            val email = binding.editTextUsernameEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            // Validate input fields
            if (email.isEmpty()) {
                binding.editTextUsernameEmail.error = "Email cannot be empty"
                return@setOnClickListener
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.editTextUsernameEmail.error = "Invalid email format"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.editTextPassword.error = "Password cannot be empty"
                return@setOnClickListener
            }

            // Attempt Firebase login
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Sign-in successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val errorMessage = task.exception?.message ?: "Sign-in failed. Please try again."
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Navigate to Sign-Up page
        binding.textViewCreateNewAccount.setOnClickListener {
            val intent = Intent(this, LogUpActivity::class.java)
            startActivity(intent)
        }

        // Handle password reset
        binding.textViewResetPassword.setOnClickListener {
            val email = binding.editTextUsernameEmail.text.toString().trim()
            if (email.isEmpty()) {
                binding.editTextUsernameEmail.error = "Please enter your registered email address"
                return@setOnClickListener
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.editTextUsernameEmail.error = "Invalid email format"
                return@setOnClickListener
            }

            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password reset email sent. Check your inbox.", Toast.LENGTH_LONG).show()
                } else {
                    val errorMessage = task.exception?.message ?: "Failed to send password reset email."
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
