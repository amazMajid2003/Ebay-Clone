package com.example.ebay_clone.ui.myebay

import OrdersAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebay_clone.Order
import com.example.ebay_clone.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PurchasesActivity : AppCompatActivity() {

    private lateinit var ordersRecyclerView: RecyclerView
    private lateinit var emptyOrdersTextView: TextView
    private lateinit var ordersAdapter: OrdersAdapter
    private val ordersList = mutableListOf<Order>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchases)

        // Initialize UI elements
        findViewById<ImageView>(R.id.Cancel_Purchases).setOnClickListener {
            finish()
        }
        findViewById<ImageView>(R.id.imageViewCart).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        ordersRecyclerView = findViewById(R.id.ordersRecyclerView)
        emptyOrdersTextView = findViewById(R.id.emptyOrdersText) // TextView for empty orders

        ordersRecyclerView.layoutManager = LinearLayoutManager(this)
        ordersAdapter = OrdersAdapter(ordersList)
        ordersRecyclerView.adapter = ordersAdapter

        fetchOrders()
    }

    private fun fetchOrders() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val database = FirebaseDatabase.getInstance().getReference("Orders")

            database.orderByChild("userId").equalTo(userId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        ordersList.clear()
                        for (orderSnapshot in snapshot.children) {
                            val order = orderSnapshot.getValue(Order::class.java)
                            if (order != null) {
                                ordersList.add(order)
                            }
                        }
                        updateUI() // Update the UI based on ordersList
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@PurchasesActivity, "Failed to load orders: ${error.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    private fun updateUI() {
        val linearLayoutPurchases = findViewById<LinearLayout>(R.id.LinearLayoutPurchases) // Access the LinearLayout

        if (ordersList.isEmpty()) {
            // Show the empty message layout and hide the RecyclerView
            linearLayoutPurchases.visibility = View.VISIBLE
            ordersRecyclerView.visibility = View.GONE
        } else {
            // Hide the empty message layout and show the RecyclerView
            linearLayoutPurchases.visibility = View.GONE
            ordersRecyclerView.visibility = View.VISIBLE
        }
        ordersAdapter.notifyDataSetChanged()
    }

}
