package com.example.ebay_clone.ui.myebay

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ebay_clone.Cart
import com.example.ebay_clone.CartItem
import com.example.ebay_clone.Product
import com.example.ebay_clone.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.NumberFormat
import java.util.Locale

class ProductActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var productId: String
    private lateinit var product: Product
    private var isSaved = false // To track the saved state of the product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        // Initialize Firebase reference
        database = FirebaseDatabase.getInstance().getReference("Products")

        // Retrieve product details from the intent
        productId = intent.getStringExtra("ProductId") ?: ""
        product = Product(
            Name = intent.getStringExtra("ProductName") ?: "Unknown Product",
            Brand = intent.getStringExtra("Brand") ?: "Unknown Brand",
            Price = intent.getIntExtra("Price", 0),
            Discounted_Price = intent.getIntExtra("DiscountedPrice", 0),
            Description = intent.getStringExtra("Description") ?: "A great product. This will suit your taste.",
            Saved = intent.getBooleanExtra("Saved", false),
            turl = intent.getStringExtra("ImageURL") ?: "",
            DiscountPercent = intent.getStringExtra("DiscountPercent") ?: "0", // Default to 0 if no discount is provided
            CategoryName = intent.getStringExtra("CategoryName") ?: ""
        )
        isSaved = product.Saved

        // Log retrieved data
        Log.d("ProductActivity", "Loaded product: $product")

        // Format Price and Discounted Price
        val formattedPrice = formatPrice(product.Price)
        val formattedDiscountedPrice = formatPrice(product.Discounted_Price)

        // Populate UI elements with data
        findViewById<TextView>(R.id.textViewProductName).text = product.Name
        findViewById<TextView>(R.id.textViewBrandName).text = product.Brand
        findViewById<TextView>(R.id.textViewPrice).text = "Price: $formattedPrice"
        findViewById<TextView>(R.id.textViewDescription).text = product.Description
        findViewById<TextView>(R.id.textViewDiscountedPrice).text = "Discounted Price: $formattedDiscountedPrice"

        // Set the Category Name and Discount Percent
        findViewById<TextView>(R.id.textViewCategoryName).text = "Category: ${product.CategoryName}"
        findViewById<TextView>(R.id.textViewDiscountPercent).text = "Discount: ${product.DiscountPercent}"

        // Load the image using Glide
        findViewById<ImageView>(R.id.imageViewProduct).let {
            Glide.with(this).load(product.turl).into(it)
        }

        // Set the initial heart icon state
        val heartIcon = findViewById<ImageView>(R.id.imageViewHeart)
        updateHeartIcon(heartIcon)

        // Heart icon toggle for saving the product
        heartIcon.setOnClickListener {
            isSaved = !isSaved
            product = product.copy(Saved = isSaved) // Update the local product state
            updateHeartIcon(heartIcon)
            updateProductInFirebase(productId, isSaved)
        }

        // Add to cart functionality
        findViewById<Button>(R.id.CartButton).setOnClickListener {
            val cartItem = CartItem(product.Name, product.Brand, product.Price, product.Discounted_Price, product.turl)
            Cart.addItem(cartItem)
            Toast.makeText(this, "${product.Name} added to cart!", Toast.LENGTH_SHORT).show()
        }

        // Navigate to cart
        findViewById<ImageView>(R.id.imageViewCart).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        // Close product activity
        findViewById<ImageView>(R.id.Cancel_Product).setOnClickListener {
            finish()
        }
    }

    // Update the product in Firebase Realtime Database
    private fun updateProductInFirebase(productId: String, saved: Boolean) {
        if (productId.isNotEmpty()) {
            database.child(productId).child("Saved").setValue(saved)
                .addOnSuccessListener {
                    Toast.makeText(this, "Product saved successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save product", Toast.LENGTH_SHORT).show()
                }
        }
    }

    // Update the heart icon based on the saved state
    private fun updateHeartIcon(heartIcon: ImageView) {
        if (isSaved) {
            heartIcon.setImageResource(R.drawable.heart_angle_svgrepo_com)
        } else {
            heartIcon.setImageResource(R.drawable.heart_angle_svgrepo_com__1_)
        }
    }

    // Format the price to currency format
    private fun formatPrice(price: Int): String {
        val numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault())
        return numberFormat.format(price)
    }
}
