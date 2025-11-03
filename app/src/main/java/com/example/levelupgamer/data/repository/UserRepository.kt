package com.example.levelupgamer.data.repository

import com.example.levelupgamer.data.local.dao.UserDao
import com.example.levelupgamer.data.local.entity.User

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(user: User): Result<Unit> {
        val existingUser = userDao.getUserByEmail(user.email)
        return if (existingUser != null) {
            Result.failure(Exception("El email ya está registrado"))
        } else {
            userDao.registerUser(user)
            Result.success(Unit)
        }
    }

    suspend fun login(email: String, password: String): Result<User> {
        val user = userDao.login(email, password)
        return if (user != null) Result.success(user)
        else Result.failure(Exception("Credenciales incorrectas"))
    }
}
