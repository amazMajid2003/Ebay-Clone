package com.example.ebay_clone.ui.myebay

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ebay_clone.Product
import com.example.ebay_clone.R

class RecentlyViewedAdapter(
    private val context: Context,
    private val recentlyViewedProducts: List<Product>
) : RecyclerView.Adapter<RecentlyViewedAdapter.RecentlyViewedViewHolder>() {

    class RecentlyViewedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.productImage)
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val productBrand: TextView = itemView.findViewById(R.id.productBrand)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyViewedViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recently_viewed_item, parent, false)
        return RecentlyViewedViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecentlyViewedViewHolder, position: Int) {
        val product = recentlyViewedProducts[position]

        // Set product details to the view holder
        holder.productName.text = product.Name ?: "Unknown Product"
        holder.productPrice.text = "$${product.Price}"
        holder.productBrand.text = product.Brand

        // Load the product image using Glide
        val imageUrl = product.turl
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(context)
                .load(imageUrl)
                .into(holder.productImage)
        }

        // Handle item click to navigate to ProductActivity
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductActivity::class.java).apply {
                putExtra("ProductId", product.Id)  // Pass ProductId
                putExtra("ProductName", product.Name ?: "Unknown Product")
                putExtra("Brand", product.Brand)
                putExtra("Price", product.Price)
                putExtra("DiscountedPrice", product.Discounted_Price)
                putExtra("Description", product.Description ?: "A great product. This will suit your taste.")
                putExtra("Saved", product.Saved)
                putExtra("ImageURL", product.turl)
                putExtra("DiscountPercent", product.DiscountPercent)
                putExtra("CategoryName", product.CategoryName)
            }
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int = recentlyViewedProducts.size
}
