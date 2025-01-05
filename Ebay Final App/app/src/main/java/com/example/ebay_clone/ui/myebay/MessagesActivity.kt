package com.example.ebay_clone.ui.myebay

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.ebay_clone.R

class MessagesActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)  // Inflate the XML layout
        val image= findViewById<ImageView>(R.id.Cancel_Messages)
        image.setOnClickListener{
            finish()
        }
        findViewById<ImageView>(R.id.imageViewCart).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }
}