package com.example.ebay_clone.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ebay_clone.Product
import com.example.ebay_clone.databinding.FragmentSearchBinding
import com.example.ebay_clone.ui.myebay.ProductActivity
import com.google.firebase.database.*
import android.widget.SearchView

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var databaseReference: DatabaseReference
    private val productList = mutableListOf<Product>()
    private val filteredProductList = mutableListOf<Product>()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Products")

        // Initialize RecyclerView and Adapter
        initRecyclerView()

        // Fetch products from Firebase
        fetchProducts()

        // Add listener for search query changes
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // You can perform some action here when the user submits the search
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter the products based on the query text
                filterProducts(newText)
                return true
            }
        })

        return root
    }

    // Function to initialize RecyclerView and Adapter
    private fun initRecyclerView() {
        productAdapter = ProductAdapter(requireContext(), filteredProductList) // Pass the context and filtered list
        binding.recyclerViewSearchResults.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }
    }

    // Function to fetch products from Firebase
    private fun fetchProducts() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear the list before adding new data
                productList.clear()

                // Parse the data from Firebase snapshot
                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.getValue(Product::class.java)
                    product?.let {
                        productList.add(it) // Add product to the list
                    }
                }

                // Initially, display all products
                filteredProductList.addAll(productList)

                // Notify the adapter that the data has changed
                productAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Log the error and show a user-friendly message
                Log.e("FirebaseError", "Failed to fetch products: ${error.message}")
                Toast.makeText(requireContext(), "Unable to fetch products at the moment", Toast.LENGTH_LONG).show()
            }
        })
    }

    // Function to filter products based on the query
    private fun filterProducts(query: String?) {
        filteredProductList.clear()

        if (query.isNullOrEmpty()) {
            // If query is empty, show all products
            filteredProductList.addAll(productList)
        } else {
            // Filter products based on name, brand, or description (adjust as needed)
            for (product in productList) {
                if (product.Name.contains(query, ignoreCase = true) ||
                    product.Brand.contains(query, ignoreCase = true) ||
                    product.Description.contains(query, ignoreCase = true)) {
                    filteredProductList.add(product)
                }
            }
        }

        // Notify the adapter that the data has changed
        productAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
