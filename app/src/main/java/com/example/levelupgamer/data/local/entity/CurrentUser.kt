package com.example.levelupgamer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_user")
data class CurrentUser(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val email: String,
    val nombre: String,
    val fotoPerfil: String? = null
)
