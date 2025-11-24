package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.levelupgamer.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {

    private val _items = MutableStateFlow<List<Product>>(emptyList())
    val items: StateFlow<List<Product>> = _items

    fun add(product: Product) {
        _items.value = _items.value + product
    }

    fun remove(product: Product) {
        _items.value = _items.value - product
    }

    fun clear() {
        _items.value = emptyList()
    }

    private fun priceToInt(price: String): Int {
        // deja solo dígitos (ej: "12.990 CLP" -> "12990")
        val digits = price.filter { it.isDigit() }
        return digits.toIntOrNull() ?: 0
    }

    fun totalCLP(): Int {
        return _items.value.sumOf { priceToInt(it.price) }
    }
}
