package com.example.ebay_clone.ui.myebay

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.ebay_clone.R

class BuyAgainActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_again)
        val image= findViewById<ImageView>(R.id.Cancel_buyagain)
        image.setOnClickListener{
            finish()
        }
    }
}