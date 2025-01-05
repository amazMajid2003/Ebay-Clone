package com.example.ebay_clone.ui.myebay

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.ebay_clone.R
import com.example.ebay_clone.Product
import com.example.ebay_clone.RecentlyViewed

class DealsAdapter(
    private val context: Context,
    private val deals: List<Product>,
    private val recentlyViewed: RecentlyViewed
) : RecyclerView.Adapter<DealsAdapter.DealViewHolder>() {

    class DealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dealImage: ImageView = itemView.findViewById(R.id.dealImage)
        val dealName: TextView = itemView.findViewById(R.id.dealName)
        val dealBrand: TextView = itemView.findViewById(R.id.dealBrand)
        val dealPrice: TextView = itemView.findViewById(R.id.dealPrice)
        val dealDiscountedPrice: TextView = itemView.findViewById(R.id.dealDiscountedPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.deal_card, parent, false)
        return DealViewHolder(view)
    }

    override fun onBindViewHolder(holder: DealViewHolder, position: Int) {
        val product = deals[position]

        // Set product details in the holder
        holder.dealName.text = product.Name
        holder.dealBrand.text = "Brand: ${product.Brand}"
        holder.dealPrice.text = "Price: $${product.Price}"
        holder.dealDiscountedPrice.text = "Discounted: $${product.Discounted_Price}"

        // Load product image using Glide
        if (!product.turl.isNullOrEmpty()) {
            Glide.with(context)
                .load(product.turl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.dealImage)
        } else {
            holder.dealImage.setImageResource(R.drawable.camera)
        }

        // Set item click listener to navigate to ProductActivity and update RecentlyViewed
        holder.itemView.setOnClickListener {
            // Add product to RecentlyViewed list
            RecentlyViewed.addProduct(product)

            // Create an intent and add all product attributes
            val intent = Intent(context, ProductActivity::class.java).apply {
                putExtra("ProductId", product.Id)
                putExtra("ProductName", product.Name)
                putExtra("Brand", product.Brand)
                putExtra("Price", product.Price)
                putExtra("DiscountedPrice", product.Discounted_Price)
                putExtra("ImageURL", product.turl)
                putExtra("Description", product.Description)
                putExtra("Saved", product.Saved)
                putExtra("DiscountPercent", product.DiscountPercent)
                putExtra("CategoryName", product.CategoryName)
            }
            context.startActivity(intent)
        }

        Log.d("DealsAdapter", "Image URL: ${product.turl}")
    }

    override fun getItemCount(): Int = deals.size
}
