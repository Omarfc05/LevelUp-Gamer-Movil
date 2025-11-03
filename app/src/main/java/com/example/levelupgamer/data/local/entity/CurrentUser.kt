package com.example.levelupgamer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_user")
data class CurrentUser(
    @PrimaryKey val userId: Int,
    val email: String,
    val name: String,
    val profileImageUri: String?
)
