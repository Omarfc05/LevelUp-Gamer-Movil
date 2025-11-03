package com.example.levelupgamer.data.repository

import com.example.levelupgamer.data.local.dao.CurrentUserDao
import com.example.levelupgamer.data.local.entity.CurrentUser

class CurrentUserRepository(private val currentUserDao: CurrentUserDao) {

    suspend fun setCurrentUser(user: CurrentUser) {
        currentUserDao.clear()
        currentUserDao.insertCurrentUser(user)
    }

    suspend fun getCurrentUser(): CurrentUser? {
        return currentUserDao.getCurrentUser()
    }

    suspend fun logout() {
        currentUserDao.clear()
    }
}

