package com.example.levelupgamer.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.levelupgamer.R
import com.example.levelupgamer.model.Product

class CatalogViewModel : ViewModel() {

    var products by mutableStateOf(listOf<Product>())
        private set

    init {
        loadProducts()
    }

    private fun loadProducts() {
        products = listOf(
            Product("Teclado Mecánico", "$120", R.drawable.keyboard),
            Product("Mouse Gamer", "$80", R.drawable.mouse),
            Product("Auriculares RGB", "$90", R.drawable.headset),
            Product("Silla Gamer", "$200", R.drawable.chair),
            Product("Monitor 144Hz", "$250", R.drawable.monitor),
            Product("Mousepad XL", "$30", R.drawable.mousepad)
        )
    }
}
