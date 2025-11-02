package com.example.levelupgamer.data.repository



import com.example.levelupgamer.data.local.dao.UserDao
import com.example.levelupgamer.data.local.entity.UserEntity

class UserRepository(private val userDao: UserDao) {

    suspend fun register(email: String, password: String): Boolean {
        return try {
            userDao.registerUser(UserEntity(email = email, password = password))
            true
        } catch (e: Exception) {
            false // email existente o error
        }
    }

    suspend fun login(email: String, password: String): UserEntity? {
        return userDao.login(email, password)
    }
}
