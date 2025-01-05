package com.example.ebay_clone

data class Order(
    val orderDate: String = "",
    val orderItems: List<OrderItem> = emptyList(),
    val totalAmount: Int = 0,
    val userId: String = ""
)

