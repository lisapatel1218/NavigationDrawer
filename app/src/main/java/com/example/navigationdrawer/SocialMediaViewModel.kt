package com.example.navigationdrawer
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
class SocialMediaViewModel (private val repository: PostRepository) : ViewModel() {

    val allPosts: LiveData<List<post>> = repository.allPosts

    fun insert(post: post) = viewModelScope.launch {
        repository.insert(post)
    }
    fun likePost(post: post) {
        viewModelScope.launch {
            val updatedLikesCount = if (post.isLiked) post.likesCount - 1 else post.likesCount + 1
            val updatedPost = post.copy(isLiked = !post.isLiked, likesCount = updatedLikesCount)
            repository.updatePost(updatedPost)
        }
    }
    fun updatePost(post: post) = viewModelScope.launch {
        repository.updatePost(post) // Make sure your repository has an update method
    }


}
