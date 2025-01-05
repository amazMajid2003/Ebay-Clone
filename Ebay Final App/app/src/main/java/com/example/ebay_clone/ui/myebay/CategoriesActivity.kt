package com.example.ebay_clone.ui.myebay

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebay_clone.R
import com.google.firebase.database.*
import com.example.ebay_clone.Category

class CategoriesActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var categoriesAdapter: CategoriesAdapter
    private val categoriesList = mutableListOf<Category>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        // Initialize views and database reference
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView)
        categoriesRecyclerView.layoutManager = GridLayoutManager(this, 2) // 2 columns
        categoriesAdapter = CategoriesAdapter(this, categoriesList) { categoryName ->
            // Navigate to ProductsActivity with the selected category name
            val intent = Intent(this, ProductsActivity::class.java)
            intent.putExtra("CATEGORY_NAME", categoryName)
            startActivity(intent)
        }
        categoriesRecyclerView.adapter = categoriesAdapter

        database = FirebaseDatabase.getInstance().getReference("Category")

        // Back button functionality
        val backButton = findViewById<ImageView>(R.id.Cancel_Categories)
        backButton.setOnClickListener { finish() }

        // Fetch categories from Firebase
        fetchCategories()
    }

    private fun fetchCategories() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                categoriesList.clear()
                for (categorySnapshot in snapshot.children) {
                    val name = categorySnapshot.child("Name").getValue(String::class.java)
                    val url = categorySnapshot.child("turl").getValue(String::class.java)
                    if (name != null && url != null) {
                        categoriesList.add(Category(name, url))
                    }
                }
                categoriesAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("CategoriesActivity", "Database Error: ${error.message}")
            }
        })
    }
}
