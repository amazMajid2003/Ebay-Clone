package com.example.ebay_clone.ui.myebay

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.ebay_clone.R
class PaymentsActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payments)
        val imageViewSearch = findViewById<ImageView>(R.id.Cancel_Payments)
        imageViewSearch.setOnClickListener {
            finish()  // Finish the activity to go back
        }
    }
}