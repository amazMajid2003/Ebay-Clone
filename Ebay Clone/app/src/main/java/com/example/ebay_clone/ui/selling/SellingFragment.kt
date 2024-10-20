package com.example.ebay_clone.ui.selling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ebay_clone.databinding.FragmentSellingBinding
import com.example.ebay_clone.ui.search.SearchViewModel

class SellingFragment:Fragment() {
    private var _binding: FragmentSellingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sellingViewModel =
            ViewModelProvider(this).get(SellingViewModel::class.java)

        _binding = FragmentSellingBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}