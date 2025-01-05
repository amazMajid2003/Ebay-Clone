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
import com.google.firebase.auth.FirebaseAuth

class MyebayFragment : Fragment() {

    private var _binding: FragmentMyebayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyebayBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set up ViewModel (if needed)
        val myeBayViewModel = ViewModelProvider(this).get(MyebayViewModel::class.java)

        // Handle logout functionality
        binding.linearLayoutMessages12.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            // Navigate to the login screen and clear activity stack
            val intent = Intent(requireContext(), LogInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // Set up click listeners for navigation
        setupClickListeners()

        return root
    }

    private fun setupClickListeners() {
        val navigationMap = mapOf(
            binding.linearLayoutMessages to MessagesActivity::class.java,
            binding.linearLayoutMessages3 to PurchasesActivity::class.java,
            binding.linearLayoutMessages7 to RecentlyViewedActivity::class.java,
            binding.linearLayoutMessages4 to BidsAndOffersActivity::class.java,
            binding.linearLayoutMessages6 to SavedActivity::class.java,
            binding.linearLayoutMessages1 to WatchlistActivity::class.java,
            binding.imageViewCart to CartActivity::class.java,
            binding.linearLayoutMessages8 to CategoriesActivity::class.java,
            binding.linearLayoutMessages10 to PaymentsActivity::class.java,
            binding.linearLayoutMessages9 to Deals_Activity::class.java,
            binding.linearLayoutMessages11 to SettingsActivity::class.java
        )

        navigationMap.forEach { (linearLayout, activity) ->
            linearLayout.setOnClickListener {
                val intent = Intent(requireContext(), activity)
                startActivity(intent)
            }
        }

        binding.linearLayoutMessages5.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_myebay_to_navigation_selling)
        }

        binding.imageViewSearch.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_myebay_to_navigation_search)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
