package com.example.levelupgamer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val email: String,
    val password: String,
    val fotoPerfil: String? = null // URI de la imagen de perfil (puede ser null)
)
