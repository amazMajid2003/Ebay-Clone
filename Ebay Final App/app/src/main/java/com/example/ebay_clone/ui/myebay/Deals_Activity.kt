package com.example.ebay_clone.ui.myebay

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebay_clone.Product
import com.example.ebay_clone.R
import com.example.ebay_clone.RecentlyViewed
import com.google.firebase.database.*

class Deals_Activity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var dealsRecyclerView: RecyclerView
    private lateinit var dealsAdapter: DealsAdapter
    private val dealsList = mutableListOf<Product>()  // Using Product class to hold products

    // Global reference to RecentlyViewed Singleton
    private val recentlyViewed = RecentlyViewed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deals)

        // Initialize RecyclerView and Adapter
        dealsRecyclerView = findViewById(R.id.dealsRecyclerView)
        dealsRecyclerView.layoutManager = GridLayoutManager(this, 2) // 2 items per row
        dealsAdapter = DealsAdapter(this, dealsList, recentlyViewed) // Pass recentlyViewed to the adapter
        dealsRecyclerView.adapter = dealsAdapter

        // Back button functionality
        val backButton = findViewById<ImageView>(R.id.Cancel_Deals)
        backButton.setOnClickListener { finish() }

        // Firebase Database Reference
        database = FirebaseDatabase.getInstance().getReference("Products")

        // Fetch products filtered by Discounted Price
        fetchDeals()
        findViewById<ImageView>(R.id.imageViewCart).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun fetchDeals() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dealsList.clear() // Clear previous deals before adding new ones
                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.getValue(Product::class.java)
                    if (product != null) {
                        val price = product.Price
                        val discountedPrice = product.Discounted_Price

                        // Check if the price and discounted price are not equal, meaning it's a deal
                        if (price != discountedPrice) {
                            // Add the Firebase key as the Product ID
                            val productWithId = product.copy(Id = productSnapshot.key ?: "")
                            dealsList.add(productWithId)


                        }
                    }
                }
                dealsAdapter.notifyDataSetChanged() // Notify the adapter about changes
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("DealsActivity", "Database Error: ${error.message}")
            }
        })
    }
}
