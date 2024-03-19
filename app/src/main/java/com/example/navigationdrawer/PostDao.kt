package com.example.navigationdrawer

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: post)

    @Query("SELECT * FROM post_table ORDER BY id DESC")
    fun getAllPosts(): LiveData<List<post>>

    @Update
    suspend fun update(post: post)
}
