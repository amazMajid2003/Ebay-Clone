package com.example.ebay_clone


object RecentlyViewed {
    private val products = mutableListOf<Product>()

    fun getProducts(): List<Product> {
        return products
    }

    fun addProduct(product: Product) {
        // Optionally, you could limit the size of this list, remove duplicates, etc.
        if (!products.contains(product)) {
            products.add(product)
        }
    }

    fun clear() {
        products.clear()
    }
}
