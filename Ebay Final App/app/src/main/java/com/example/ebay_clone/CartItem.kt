package com.example.ebay_clone

data class CartItem(
    val productName: String,
    val brand: String,
    val price: Int,
    val discountedPrice: Int,
    val imageUrl: String
)
