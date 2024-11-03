package com.example.ebay_clone.ui.myebay

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.ebay_clone.R

class Deals_Activity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deals)
        val imageViewSearch = findViewById<ImageView>(R.id.Cancel_Deals)
        imageViewSearch.setOnClickListener {
            finish()  // Finish the activity to go back
        }
    }
}