package com.example.ebay_clone.ui.myebay

import android.content.Intent  // Add this import
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ebay_clone.databinding.FragmentMyebayBinding
import com.example.ebay_clone.ui.myebay.SignInActivity // Import SignInActivity

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

        // Set up OnClickListener for LinearLayout
        binding.linearLayoutProfile.setOnClickListener {
            // Navigate to the SignInActivity when clicked
            val intent = Intent(requireContext(), SignInActivity::class.java)
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
