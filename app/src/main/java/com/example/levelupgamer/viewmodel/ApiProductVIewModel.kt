package com.example.levelupgamer.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.remote.RetrofitInstance
import com.example.levelupgamer.model.ApiProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ApiProductViewModel : ViewModel() {

    private val _apiProducts = MutableStateFlow<List<ApiProduct>>(emptyList())
    val apiProducts: StateFlow<List<ApiProduct>> = _apiProducts

    init {
        viewModelScope.launch {
            try {
                _apiProducts.value = RetrofitInstance.api.getProducts()
            } catch (e: Exception) {
                _apiProducts.value = emptyList()
            }
        }
    }
}
