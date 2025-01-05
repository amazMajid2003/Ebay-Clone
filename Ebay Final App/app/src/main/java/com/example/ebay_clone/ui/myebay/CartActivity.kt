package com.example.ebay_clone.ui.myebay

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ebay_clone.Cart
import com.example.ebay_clone.CartItem
import com.example.ebay_clone.Order
import com.example.ebay_clone.OrderItem
import com.example.ebay_clone.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth

class CartActivity : AppCompatActivity() {

    private lateinit var cartContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cartContainer = findViewById(R.id.cartContainer)

        // Retrieve all cart items
        val cartItems: List<CartItem> = Cart.getItems() // Assuming this returns a list of CartItem

        // Dynamically add views for each cart item
        for (item in cartItems) {
            val itemView = layoutInflater.inflate(R.layout.item_cart, cartContainer, false)

            // Set the data for the cart item
            itemView.findViewById<TextView>(R.id.textViewCartProductName).text = item.productName
            itemView.findViewById<TextView>(R.id.textViewCartBrand).text = item.brand
            itemView.findViewById<TextView>(R.id.textViewCartPrice).text = "Price: $${item.price}"
            itemView.findViewById<TextView>(R.id.textViewCartDiscountedPrice).text =
                "Discounted Price: $${item.discountedPrice}"

            // Load the image using Glide
            val productImage: ImageView = itemView.findViewById(R.id.imageViewCartProduct)
            Glide.with(this)
                .load(item.imageUrl)
                .into(productImage)

            // Add the item view to the container
            cartContainer.addView(itemView)
        }

        // Handle Cancel Cart button
        findViewById<ImageView>(R.id.Cancel_Cart).setOnClickListener {
            finish()
        }

        // Handle Clear Cart button
        findViewById<Button>(R.id.ClearCartButton).setOnClickListener {
            // Clear all items in the cart
            Cart.clearAllItems()

            // Create a new intent to restart the CartActivity
            val intent = Intent(this, CartActivity::class.java)

            // Optionally, clear the current activity stack and restart CartActivity
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

            // Restart the CartActivity
            startActivity(intent)

            // Optional: Finish the current activity if you want to ensure it's removed from the stack
            finish()
        }

        // Handle Place Order button
        findViewById<Button>(R.id.PlaceOrder).setOnClickListener {
            // Place the order
            placeOrder()


        }

    }




    private fun placeOrder() {
        // Get cart items
        val cartItems: List<CartItem> = Cart.getItems()

        if (cartItems.isEmpty()) {
            Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show()
            return
        }

        // Calculate the total price (use discounted price if available, otherwise use regular price)
        val totalAmount = cartItems.sumByDouble {
            // Check if discounted price is available, otherwise use regular price
            (it.discountedPrice.takeIf { it > 0 } ?: it.price).toDouble()
        }

        // Get the current user from Firebase Authentication
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userId = currentUser.uid // Get the user ID

            // Format the current date in a readable format
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val formattedDate = dateFormat.format(Date())

            // Map CartItem to OrderItem
            val orderItems = cartItems.map { cartItem ->
                OrderItem(
                    productName = cartItem.productName,
                    price = cartItem.price,
                    discountedPrice = cartItem.discountedPrice,
                    imageUrl = cartItem.imageUrl,
                    brand = cartItem.brand
                )
            }

            // Create a new order object with a human-readable date
            val order = Order(
                userId = userId, // Use the actual user ID from Firebase Authentication
                orderItems = orderItems, // Pass the mapped list of OrderItems
                totalAmount = totalAmount.toInt(),
                orderDate = formattedDate // Use the formatted date here
            )

            // Get a reference to the Firebase Database
            val database = FirebaseDatabase.getInstance().getReference("Orders")

            // Push the order to Firebase
            val orderId = database.push().key
            if (orderId != null) {
                database.child(orderId).setValue(order)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show()

                        // Clear the cart after order is placed
                        Cart.clearAllItems()

                        // Restart CartActivity or navigate to a confirmation screen
                        val intent = Intent(this, CartActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "Failed to place order: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        } else {
            Toast.makeText(this, "User is not logged in", Toast.LENGTH_SHORT).show()
        }
    }

}
