package com.example.ebay_clone.ui.myebay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ebay_clone.CartItem
import com.example.ebay_clone.R

class CartAdapter(private val cartItems: List<CartItem>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    // ViewHolder to hold each item view
    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.textViewCartProductName)
        val brand: TextView = view.findViewById(R.id.textViewCartBrand)
        val price: TextView = view.findViewById(R.id.textViewCartPrice)
        val discountedPrice: TextView = view.findViewById(R.id.textViewCartDiscountedPrice)
        val image: ImageView = view.findViewById(R.id.imageViewCartProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]

        holder.productName.text = cartItem.productName
        holder.brand.text = cartItem.brand
        holder.price.text = "Price: $${cartItem.price}"
        holder.discountedPrice.text = "Discounted Price: $${cartItem.discountedPrice}"

        // Load product image using Glide
        Glide.with(holder.itemView.context)
            .load(cartItem.imageUrl)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }
}
