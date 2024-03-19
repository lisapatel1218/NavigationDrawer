// UserDao.kt
package com.example.navigationdrawer.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// UserDao.kt
@Dao
interface UserDao {
    @Insert
    fun insertUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Update
    fun updateUser(user: UserEntity)

    @Query("SELECT * FROM Users")
    fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM Users WHERE id = :userId")
    suspend fun getUserById(userId: Long): UserEntity?


}
