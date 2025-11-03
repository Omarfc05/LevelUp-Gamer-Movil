package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.levelupgamer.data.repository.CurrentUserRepository
import com.example.levelupgamer.data.repository.UserRepository

class AuthViewModelFactory(
    private val userRepository: UserRepository,
    private val currentUserRepository: CurrentUserRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(userRepository, currentUserRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
