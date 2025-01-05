package com.example.ebay_clone.ui.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.ebay_clone.Product
import com.example.ebay_clone.R
import com.example.ebay_clone.RecentlyViewed
import com.example.ebay_clone.databinding.SavedItemBinding
import com.example.ebay_clone.ui.myebay.ProductActivity // Assuming the activity is in the "product" package

class ProductAdapter(private val context: Context, private val productList: MutableList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = SavedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)

        // Set item click listener to navigate to ProductActivity and send all product data
        holder.itemView.setOnClickListener {
            RecentlyViewed.addProduct(product)
            // Create an intent and send all necessary product details
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
    }

    override fun getItemCount(): Int = productList.size

    class ProductViewHolder(private val binding: SavedItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productName.text = product.Name
            binding.productBrand.text = "Brand: ${product.Brand}"
            binding.productPrice.text = "Price: $${product.Price}"
            binding.productDescription.text = product.Description

            Glide.with(binding.productImage.context)
                .load(product.turl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(binding.productImage)
        }
    }
}
