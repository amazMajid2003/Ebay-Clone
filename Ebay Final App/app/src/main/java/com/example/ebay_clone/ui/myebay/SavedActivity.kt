package com.example.ebay_clone.ui.myebay

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebay_clone.Product
import com.example.ebay_clone.R
import com.google.firebase.database.*

class SavedActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var savedRecyclerView: RecyclerView
    private lateinit var savedAdapter: SavedProductsAdapter
    private val savedProductsList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved)

        // Set up Cancel button
        findViewById<ImageView>(R.id.Cancel_Saved).setOnClickListener {
            finish()
        }
        findViewById<ImageView>(R.id.imageViewCart).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        // Initialize RecyclerView
        savedRecyclerView = findViewById(R.id.savedRecyclerView)
        savedRecyclerView.layoutManager = LinearLayoutManager(this)
        savedAdapter = SavedProductsAdapter(this, savedProductsList)  // Use SavedProductsAdapter
        savedRecyclerView.adapter = savedAdapter

        // Firebase Database Reference
        database = FirebaseDatabase.getInstance().getReference("Products")

        // Fetch saved products initially
        fetchSavedProducts()
    }

    override fun onResume() {
        super.onResume()
        // Fetch saved products when the activity is resumed
        fetchSavedProducts()
    }

    private fun fetchSavedProducts() {
        database.orderByChild("Saved").equalTo(true)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    savedProductsList.clear()
                    for (productSnapshot in snapshot.children) {
                        val product = productSnapshot.getValue(Product::class.java)
                        if (product != null) {
                            val productWithId = product.copy(Id = productSnapshot.key ?: "")
                            savedProductsList.add(productWithId)
                        }
                    }

                    // Handle visibility of RecyclerView or empty message
                    updateUI()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("SavedActivity", "Database Error: ${error.message}")
                }
            })
    }

    private fun updateUI() {
        if (savedProductsList.isEmpty()) {
            findViewById<RecyclerView>(R.id.savedRecyclerView).visibility = View.GONE
            findViewById<View>(R.id.scrollView3).visibility = View.VISIBLE
        } else {
            findViewById<RecyclerView>(R.id.savedRecyclerView).visibility = View.VISIBLE
            findViewById<View>(R.id.scrollView3).visibility = View.GONE
        }
        savedAdapter.notifyDataSetChanged()
    }
}
