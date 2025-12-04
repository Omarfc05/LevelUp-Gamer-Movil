package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.R
import com.example.levelupgamer.data.local.dao.ProductDao
import com.example.levelupgamer.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val productDao: ProductDao) : ViewModel() {

    // Usamos StateFlow para observar los datos de la BD
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    init {
        // 1. Observar la base de datos
        viewModelScope.launch {
            productDao.getAllProducts().collect { list ->
                if (list.isEmpty()) {
                    seedDatabase() // Si está vacía, la llenamos con los datos por defecto
                } else {
                    _products.value = list
                }
            }
        }
    }

    // Función para actualizar producto en BD
    fun updateProduct(product: Product, newName: String, newPrice: String) {
        viewModelScope.launch {
            val updatedProduct = product.copy(name = newName, price = newPrice)
            productDao.update(updatedProduct)
        }
    }

    fun addMysteryProduct() {
        viewModelScope.launch {
            val mysteryProduct = Product(
                name = "Caja Misteriosa Nivel ${ (1..100).random() }",
                price = "15.000",
                imageRes = R.drawable.logo // Usamos el logo por defecto
            )
            productDao.insert(mysteryProduct)
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            productDao.delete(product)
        }
    }

    // Mueve la función seedDatabase AQUÍ ADENTRO (antes de la última llave)
    // Datos iniciales (Seed)
    private fun seedDatabase() {
        val initialProducts = listOf(
            Product(name = "Teclado Mecánico RGB", price = "89.990", imageRes = R.drawable.keyboard),
            Product(name = "Mouse Gamer HyperSpeed", price = "49.990", imageRes = R.drawable.mouse),
            Product(name = "Auriculares Surround 7.1", price = "79.990", imageRes = R.drawable.headset),
            Product(name = "Monitor Curvo 144Hz", price = "299.990", imageRes = R.drawable.monitor),
            Product(name = "Silla Ergonómica", price = "199.990", imageRes = R.drawable.chair),
            Product(name = "Control Inalámbrico", price = "59.990", imageRes = R.drawable.controller)
        )
        viewModelScope.launch {
            productDao.insertAll(initialProducts)
        }
    }
}