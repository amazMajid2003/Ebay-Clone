package com.example.ebay_clone

data class OrderItem(
    val brand: String = "",
    val discountedPrice: Int = 0,
    val imageUrl: String = "",
    val price: Int = 0,
    val productName: String = ""
)
