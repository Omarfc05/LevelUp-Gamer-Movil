package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.remote.GamesApi
import com.example.levelupgamer.model.ApiProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ApiProductViewModel : ViewModel() {

    private val _apiProducts = MutableStateFlow<List<ApiProduct>>(emptyList())
    val apiProducts: StateFlow<List<ApiProduct>> = _apiProducts

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = GamesApi.service.getGames()
                // Te quedas con algunos para no llenar la pantalla
                _apiProducts.value = result.take(10)
            } catch (e: Exception) {
                _error.value = "Error al cargar juegos"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
