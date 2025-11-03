package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamer.data.local.entity.CurrentUser
import com.example.levelupgamer.data.local.entity.User
import com.example.levelupgamer.data.repository.CurrentUserRepository
import com.example.levelupgamer.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userRepository: UserRepository,
    private val currentUserRepository: CurrentUserRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> get() = _authState

    private val _currentUser = MutableStateFlow<CurrentUser?>(null)
    val currentUser: StateFlow<CurrentUser?> get() = _currentUser

    init {
        viewModelScope.launch {
            _currentUser.value = currentUserRepository.getCurrentUser()
        }
    }

    fun register(nombre: String, email: String, password: String, fotoPerfil: String?) {
        viewModelScope.launch {
            val result = userRepository.registerUser(
                User(nombre = nombre, email = email, password = password, fotoPerfil = fotoPerfil)
            )
            _authState.value = if (result.isSuccess) AuthState.RegisterSuccess
            else AuthState.Error(result.exceptionOrNull()?.message ?: "Error al registrar")
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = userRepository.login(email, password)
            if (result.isSuccess) {
                val user = result.getOrNull()!!
                val cu = CurrentUser(
                    userId = user.id,
                    email = user.email,
                    nombre = user.nombre,
                    fotoPerfil = user.fotoPerfil
                )
                currentUserRepository.setCurrentUser(cu)
                _currentUser.value = cu
                _authState.value = AuthState.LoginSuccess
            } else {
                _authState.value = AuthState.Error("Credenciales inválidas")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            currentUserRepository.logout()
            _currentUser.value = null
            _authState.value = AuthState.Idle
        }
    }

    fun updateProfilePhoto(uri: String) {
        viewModelScope.launch {
            _currentUser.value?.let { user ->
                val updatedUser = user.copy(fotoPerfil = uri)
                currentUserRepository.setCurrentUser(updatedUser)
                _currentUser.value = updatedUser
            }
        }
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object RegisterSuccess : AuthState()
    object LoginSuccess : AuthState()
    data class Error(val message: String) : AuthState()
}
