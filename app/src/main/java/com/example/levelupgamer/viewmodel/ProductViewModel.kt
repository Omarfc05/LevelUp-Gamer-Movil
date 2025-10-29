package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.levelupgamer.R
import com.example.levelupgamer.model.Product

class ProductViewModel : ViewModel() {

    // Productos de ejemplo (puedes cambiarlos o traerlos de un backend)
    val products = listOf(
        Product(1, "Teclado Mecánico RGB", 89.99, R.drawable.keyboard),
        Product(2, "Mouse Gamer HyperSpeed", 49.99, R.drawable.mouse),
        Product(3, "Auriculares Surround 7.1", 79.99, R.drawable.headset),
        Product(4, "Monitor Curvo 144Hz", 299.99, R.drawable.monitor),
        Product(5, "Silla Ergonómica", 199.99, R.drawable.chair),
        Product(6, "Control Inalámbrico", 59.99, R.drawable.controller)
    )
}
