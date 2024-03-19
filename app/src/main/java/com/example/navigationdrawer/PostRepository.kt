package com.example.navigationdrawer

import androidx.lifecycle.LiveData

class PostRepository(private val postDao: PostDao) {
    val allPosts: LiveData<List<post>> = postDao.getAllPosts()

    suspend fun insert(post: post) {
        postDao.insert(post)
    }

    suspend fun updatePost(post: post) {
        postDao.update(post)
    }
}