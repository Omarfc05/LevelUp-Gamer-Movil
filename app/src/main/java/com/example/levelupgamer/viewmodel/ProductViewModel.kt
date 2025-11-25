package com.example.levelupgamer.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.levelupgamer.R
import com.example.levelupgamer.model.Product

class ProductViewModel : ViewModel() {

    // Lista editable, para que Compose reaccione cuando se actualiza
    private val _products = mutableStateListOf(
        Product("Teclado Mecánico RGB", "89.990", R.drawable.keyboard),
        Product("Mouse Gamer HyperSpeed", "49.990", R.drawable.mouse),
        Product("Auriculares Surround 7.1", "79.990", R.drawable.headset),
        Product("Monitor Curvo 144Hz", "299.990", R.drawable.monitor),
        Product("Silla Ergonómica", "199.990", R.drawable.chair),
        Product("Control Inalámbrico", "59.990", R.drawable.controller)
    )

    val products: List<Product> get() = _products

    fun updateProduct(index: Int, newName: String, newPrice: String) {
        if (index in _products.indices) {
            val old = _products[index]
            _products[index] = old.copy(
                name = newName,
                price = newPrice
            )
        }
    }
}
