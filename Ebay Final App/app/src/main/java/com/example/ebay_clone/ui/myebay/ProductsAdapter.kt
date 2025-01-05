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

class ProductsAdapter(
    private val context: Context,
    private val products: List<Product>,
    private val recentlyViewed: RecentlyViewed
) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.productImage)
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productBrand: TextView = itemView.findViewById(R.id.productBrand)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val productDescription: TextView = itemView.findViewById(R.id.productDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_card, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        // Set product details in the holder
        holder.productName.text = product.Name
        holder.productBrand.text = product.Brand
        holder.productPrice.text = "$${product.Price}"
        holder.productDescription.text = product.Description

        // Load product image using Glide
        if (!product.turl.isNullOrEmpty()) {
            Glide.with(context)
                .load(product.turl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.productImage)
        } else {
            holder.productImage.setImageResource(R.drawable.camera)
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

        Log.d("ProductsAdapter", "Image URL: ${product.turl}")
    }


    override fun getItemCount(): Int = products.size
}
