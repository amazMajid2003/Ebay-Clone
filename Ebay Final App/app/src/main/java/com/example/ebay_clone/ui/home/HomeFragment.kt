package com.example.ebay_clone.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.ebay_clone.R
import com.example.ebay_clone.databinding.FragmentHomeBinding
import com.example.ebay_clone.Product
import com.example.ebay_clone.RecentlyViewed
import com.example.ebay_clone.ui.myebay.CartActivity
import com.example.ebay_clone.ui.myebay.CategoriesActivity
import com.example.ebay_clone.ui.myebay.Deals_Activity
import com.example.ebay_clone.ui.myebay.ProductActivity
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize Firebase Database reference
        database = FirebaseDatabase.getInstance().getReference("Products")

        // Fetch and display products where Price and Discounted_Price are not equal
        fetchDiscountedProductsFromFirebase()

        binding.buttonSelling.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_navigation_selling)
        }

        val navigationMap = mapOf(
            binding.buttonDeals to Deals_Activity::class.java,
            binding.Seeall to Deals_Activity::class.java,
            binding.buttonCategories to CategoriesActivity::class.java,
            binding.buttonShopNow to CategoriesActivity::class.java,
            binding.imageViewCart to CartActivity::class.java
        )

        for ((linearLayout, activity) in navigationMap) {
            linearLayout.setOnClickListener {
                val intent = Intent(requireContext(), activity)
                startActivity(intent)
            }
        }

        return root
    }

    private fun fetchDiscountedProductsFromFirebase() {
        // Query to fetch all products
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dealsLayout = binding.linearLayoutContent // Parent layout for deals
                dealsLayout.removeAllViews() // Clear previous content

                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.getValue(Product::class.java) ?: continue

                    // Ensure Price and Discounted_Price are not equal before adding
                    if (product.Price != product.Discounted_Price) {
                        // Inflate the product item layout
                        val productView = LayoutInflater.from(requireContext())
                            .inflate(R.layout.item_deal, dealsLayout, false)

                        // Bind data to views
                        val productImage = productView.findViewById<ImageView>(R.id.imageViewItem)
                        val productName = productView.findViewById<TextView>(R.id.textViewName)
                        val productBrand = productView.findViewById<TextView>(R.id.textViewBrand)
                        val productPrice = productView.findViewById<TextView>(R.id.textViewPrice)
                        val productDiscountedPrice = productView.findViewById<TextView>(R.id.textViewDiscountedPrice)

                        // Populate data into views
                        productName.text = product.Name ?: "Unknown Product"
                        productBrand.text = "Brand: ${product.Brand ?: "Unknown"}"
                        productPrice.text = "Original Price: $${product.Price}"
                        productDiscountedPrice.text = "Discounted Price: $${product.Discounted_Price}"

                        // Load the image using Glide
                        Glide.with(requireContext())
                            .load(product.turl)
                            .into(productImage)

                        // Add a click listener to navigate to ProductActivity
                        productView.setOnClickListener {
                            // Add the product to RecentlyViewed
                            RecentlyViewed.addProduct(product)

                            // Now navigate to ProductActivity
                            val intent = Intent(requireContext(), ProductActivity::class.java).apply {
                                putExtra("ProductId", productSnapshot.key) // Pass ProductId here
                                putExtra("ProductName", product.Name)
                                putExtra("Brand", product.Brand)
                                putExtra("Price", product.Price) // Pass price as Int
                                putExtra("DiscountedPrice", product.Discounted_Price) // Pass discountedPrice as Int
                                putExtra("Description", product.Description)
                                putExtra("Saved", product.Saved) // Whether it's saved
                                putExtra("ImageURL", product.turl) // Image URL
                                putExtra("DiscountPercent", product.DiscountPercent) // Discount Percent
                                putExtra("CategoryName", product.CategoryName) // Category Name
                            }
                            startActivity(intent)
                        }

                        // Add the product view to the parent layout
                        dealsLayout.addView(productView)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HomeFragment", "Firebase Database Error: ${error.message}")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
