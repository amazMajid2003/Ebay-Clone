package com.example.ebay_clone.ui.myebay

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.ebay_clone.R

class PurchasesActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchases)  // Inflate the XML layout
        val image= findViewById<ImageView>(R.id.Cancel_Purchases)
        image.setOnClickListener{
            finish()
        }
    }
}