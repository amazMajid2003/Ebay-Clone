package com.example.ebay_clone.ui.myebay

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ebay_clone.R
import com.example.ebay_clone.Category
class CategoriesAdapter(
    private val context: Context,
    private val categories: List<Category>,
    private val onCategoryClick: (String) -> Unit // Callback for item click
) : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {



    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryImage: ImageView = itemView.findViewById(R.id.categoryImage)
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_card, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.categoryName.text = category.name
        Glide.with(context)
            .load(category.url)
            .into(holder.categoryImage)

        // Handle item click
        holder.itemView.setOnClickListener {
            onCategoryClick(category.name)
        }
    }

    override fun getItemCount(): Int = categories.size
}
