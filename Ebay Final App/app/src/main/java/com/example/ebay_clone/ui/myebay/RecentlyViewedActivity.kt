package com.example.ebay_clone.ui.myebay

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebay_clone.R
import com.example.ebay_clone.Product
import com.example.ebay_clone.RecentlyViewed

class RecentlyViewedActivity : AppCompatActivity() {

    private lateinit var recentlyViewedRecyclerView: RecyclerView
    private lateinit var recentlyViewedAdapter: RecentlyViewedAdapter
    private lateinit var recentlyViewedProducts: List<Product>  // List of products

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recently_viewed)

        // Fetch the recently viewed products from the singleton
        recentlyViewedProducts = RecentlyViewed.getProducts()

        // Check if the list is empty and adjust UI visibility accordingly
        handleEmptyListVisibility()

        // Initialize RecyclerView
        recentlyViewedRecyclerView = findViewById(R.id.recentlyViewedRecyclerView)
        recentlyViewedRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize and set the adapter for the RecyclerView
        recentlyViewedAdapter = RecentlyViewedAdapter(this, recentlyViewedProducts)
        recentlyViewedRecyclerView.adapter = recentlyViewedAdapter

        // Set up Cancel button to finish the activity
        findViewById<ImageView>(R.id.Cancel_Recentlyviewed).setOnClickListener {
            finish()
        }
        findViewById<ImageView>(R.id.imageViewCart).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun handleEmptyListVisibility() {
        // Get the ScrollView and RecyclerView views
        val recyclerView = findViewById<RecyclerView>(R.id.recentlyViewedRecyclerView)
        val scrollView = findViewById<ScrollView>(R.id.scrollView5)

        // Check if the list is empty
        if (recentlyViewedProducts.isEmpty()) {
            recyclerView.visibility = View.GONE
            scrollView.visibility = View.VISIBLE
            Toast.makeText(this, "No recently viewed products", Toast.LENGTH_SHORT).show()
        } else {
            recyclerView.visibility = View.VISIBLE
            scrollView.visibility = View.GONE
        }
    }
}
