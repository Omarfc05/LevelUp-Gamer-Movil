package com.example.levelupgamer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.levelupgamer.data.local.dao.UserDao
import com.example.levelupgamer.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
