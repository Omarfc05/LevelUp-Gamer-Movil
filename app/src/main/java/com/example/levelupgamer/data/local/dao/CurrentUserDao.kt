package com.example.levelupgamer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.levelupgamer.data.local.entity.CurrentUser

@Dao
interface CurrentUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentUser(user: CurrentUser)

    @Query("SELECT * FROM current_user LIMIT 1")
    suspend fun getCurrentUser(): CurrentUser?

    @Query("DELETE FROM current_user")
    suspend fun clear()
}
