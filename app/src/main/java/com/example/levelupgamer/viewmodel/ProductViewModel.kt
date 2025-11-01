package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.levelupgamer.R
import com.example.levelupgamer.model.Product

class ProductViewModel : ViewModel() {

    val products = listOf(
        Product("Teclado Mecánico RGB", "$89.99", R.drawable.keyboard),
        Product("Mouse Gamer HyperSpeed", "$49.99", R.drawable.mouse),
        Product("Auriculares Surround 7.1", "$79.99", R.drawable.headset),
        Product("Monitor Curvo 144Hz", "$299.99", R.drawable.monitor),
        Product("Silla Ergonómica", "$199.99", R.drawable.chair),
        Product("Control Inalámbrico", "$59.99", R.drawable.controller)
    )
}
