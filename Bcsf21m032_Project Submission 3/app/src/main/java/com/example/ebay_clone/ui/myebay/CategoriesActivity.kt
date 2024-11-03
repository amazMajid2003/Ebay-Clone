package com.example.ebay_clone.ui.myebay

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.ebay_clone.R
class CategoriesActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        val imageViewSearch = findViewById<ImageView>(R.id.Cancel_Categories)
        imageViewSearch.setOnClickListener {
            finish()  // Finish the activity to go back
        }
    }
}