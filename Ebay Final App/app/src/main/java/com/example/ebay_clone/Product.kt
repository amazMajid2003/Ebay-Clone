package com.example.ebay_clone

data class Product(
    val Id: String = "", // Unique identifier for Firebase
    val DiscountPercent:String="",
    val Discounted_Price:Int=0,
    val Brand: String = "",
    val CategoryName: String = "",
    val Description: String = "",
    val Name: String = "",
    val Price: Int = 0,
    val Saved: Boolean = false,
    val turl: String = "" // Image URL
)
