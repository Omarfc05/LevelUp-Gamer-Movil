package com.example.levelupgamer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.levelupgamer.model.User

class RegisterViewModel : ViewModel() {

    private val usuarios = mutableListOf<User>()

    fun registrarUsuario(nombre: String, email: String, password: String): Boolean {
        if (usuarios.any { it.email == email }) return false // ya existe
        usuarios.add(User(nombre, email, password))
        return true
    }

    fun obtenerUsuarios(): List<User> = usuarios
}
