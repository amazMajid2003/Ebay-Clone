package com.example.ebay_clone.ui.myebay

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebay_clone.R
import com.example.ebay_clone.Product
import com.example.ebay_clone.RecentlyViewed
import com.google.firebase.database.*

class ProductsActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var productsRecyclerView: RecyclerView
    private lateinit var productsAdapter: ProductsAdapter
    private val productsList = mutableListOf<Product>()

    // Global reference to RecentlyViewed Singleton
    private val recentlyViewed = RecentlyViewed // Singleton object, no need to instantiate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        // Retrieve the category name from the Intent
        val categoryName = intent.getStringExtra("CATEGORY_NAME") ?: "Unknown Category"

        // Initialize RecyclerView and Adapter
        productsRecyclerView = findViewById(R.id.productsRecyclerView)
        productsRecyclerView.layoutManager = GridLayoutManager(this, 2) // 2 items per row
        productsAdapter = ProductsAdapter(this, productsList, recentlyViewed) // Pass recentlyViewed to the adapter
        productsRecyclerView.adapter = productsAdapter

        // Set up action for Cancel button
        findViewById<ImageView>(R.id.Cancel).setOnClickListener {
            finish()
        }

        // Set up action for Cart button
        findViewById<ImageView>(R.id.imageViewCart).setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        // Firebase Database Reference
        database = FirebaseDatabase.getInstance().getReference("Products")

        // Fetch products filtered by CategoryName
        fetchProductsByCategory(categoryName)
    }

    private fun fetchProductsByCategory(categoryName: String) {
        database.orderByChild("CategoryName").equalTo(categoryName)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Clear the existing list of products and reload it
                    productsList.clear()

                    for (productSnapshot in snapshot.children) {
                        val product = productSnapshot.getValue(Product::class.java)
                        if (product != null) {
                            // Add the Firebase key as the Product ID
                            val productWithId = product.copy(Id = productSnapshot.key ?: "")
                            productsList.add(productWithId)

                        }
                    }

                    // Notify the adapter about data changes
                    productsAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("ProductsActivity", "Database Error: ${error.message}")
                }
            })
    }
}
