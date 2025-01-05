package com.example.ebay_clone.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ebay_clone.R
import com.example.ebay_clone.databinding.FragmentNotificationsBinding
import com.example.ebay_clone.ui.myebay.CartActivity
import com.example.ebay_clone.ui.myebay.Deals_Activity

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.imageViewSearch.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_notifications_to_navigation_search)
        }
        val navigationMap = mapOf(binding.imageViewCart to CartActivity::class.java)
        for ((linearLayout, activity) in navigationMap) {
            linearLayout.setOnClickListener {
                val intent = Intent(requireContext(), activity)
                startActivity(intent)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}