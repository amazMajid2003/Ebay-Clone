package com.example.ebay_clone.ui.myebay

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ebay_clone.R
import com.example.ebay_clone.databinding.FragmentMyebayBinding
import com.example.ebay_clone.ui.myebay.*  // Import all required activities

class MyebayFragment : Fragment() {

    private var _binding: FragmentMyebayBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using View Binding
        _binding = FragmentMyebayBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set up ViewModel (if you are using one)
        val myeBayViewModel = ViewModelProvider(this).get(MyebayViewModel::class.java)

        // Map LinearLayouts to their corresponding activities
        setupClickListeners()

        return root
    }

    // Helper function to set up click listeners for each LinearLayout
    private fun setupClickListeners() {
        // A map that links each LinearLayout ID to the corresponding activity
        val navigationMap = mapOf(
            binding.linearLayoutProfile to SignInActivity::class.java,
            binding.linearLayoutMessages to MessagesActivity::class.java,
            binding.linearLayoutMessages3 to PurchasesActivity::class.java,
            binding.linearLayoutMessages7 to RecentlyViewedActivity::class.java,
            binding.linearLayoutMessages4 to BidsAndOffersActivity::class.java,
            binding.linearLayoutMessages6 to SavedActivity::class.java,
            binding.linearLayoutMessages1 to WatchlistActivity::class.java,
            binding.linearLayoutMessages13 to MyGarageActivity::class.java,
            binding.linearLayoutMessages2 to BuyAgainActivity::class.java,
            binding.linearLayoutMessages8 to CategoriesActivity::class.java,
            binding.linearLayoutMessages10 to PaymentsActivity::class.java,
            binding.linearLayoutMessages9 to Deals_Activity::class.java,
            binding.linearLayoutMessages11 to SettingsActivity::class.java
        )

        // Loop through each entry and set the click listener
        for ((linearLayout, activity) in navigationMap) {
            linearLayout.setOnClickListener {
                val intent = Intent(requireContext(), activity)
                startActivity(intent)
            }
        }
        binding.linearLayoutMessages5.setOnClickListener {
            // Navigate to SellingFragment using NavController
            findNavController().navigate(R.id.action_navigation_myebay_to_navigation_selling)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
