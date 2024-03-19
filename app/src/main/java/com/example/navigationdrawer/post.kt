package com.example.navigationdrawer

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "post_table")
@TypeConverters(Converters::class) // Use TypeConverters for the comments field
data class post(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userName: String,
    val timeAgo: String,
    val postImage: Int, // Assuming you're storing drawable resource IDs
    var likesCount: Int = 0,
    var commentsCount: Int = 0,
    var isLiked: Boolean = false,
    var comments: MutableList<String> = mutableListOf() // Store comments as a list of strings
)