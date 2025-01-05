package com.example.ebay_clone

object Cart {
    private val items = mutableListOf<CartItem>()

    fun addItem(item: CartItem) {
        items.add(item)
    }
    fun clearAllItems() {
        items.clear()
    }
    fun getItems(): List<CartItem> = items
}
