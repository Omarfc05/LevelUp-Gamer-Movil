package com.example.levelupgamer.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.levelupgamer.data.local.dao.CurrentUserDao
import com.example.levelupgamer.data.local.dao.UserDao
import com.example.levelupgamer.data.local.entity.CurrentUser
import com.example.levelupgamer.data.local.entity.User

@Database(
    entities = [User::class, CurrentUser::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun currentUserDao(): CurrentUserDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "levelup_gamer_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
