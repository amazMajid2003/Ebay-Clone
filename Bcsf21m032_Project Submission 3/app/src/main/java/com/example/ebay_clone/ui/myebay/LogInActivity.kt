package com.example.ebay_clone.ui.myebay

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.ebay_clone.R

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)  // Inflate the XML layout
        // Find the ImageView by ID and set an OnClickListener
        val imageViewSearch = findViewById<ImageView>(R.id.imageViewSearch)
        imageViewSearch.setOnClickListener {
            finish()  // Finish the activity to go back
        }
    }
}
