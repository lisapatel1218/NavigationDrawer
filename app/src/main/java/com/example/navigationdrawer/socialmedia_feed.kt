package com.example.navigationdrawer

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class socialmedia_feed : AppCompatActivity() {
    private lateinit var feedRecyclerView: RecyclerView
    private lateinit var feedAdapter: FeedAdapter
    private val viewModel: SocialMediaViewModel by viewModels {
        SocialMediaViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_socialmedia_feed)

        setupRecyclerView()
        observePosts()
    }

    private fun setupRecyclerView() {
        feedRecyclerView = findViewById(R.id.feedRecyclerView)
        feedAdapter = FeedAdapter(emptyList(),
            onLikeClicked = { post ->
                viewModel.likePost(post)
            },
            onCommentButtonClicked = { post, _ ->
                // Assuming you're using a dialog to add a comment.
                val newCommentCount = post.commentsCount + 1 // Simulate adding a comment
                val updatedPost = post.copy(commentsCount = newCommentCount)
                viewModel.updatePost(updatedPost) // You need to implement this method in your ViewModel
            }
        )
        feedRecyclerView.layoutManager = LinearLayoutManager(this)
        feedRecyclerView.adapter = feedAdapter
    }

    private fun observePosts() {
        viewModel.allPosts.observe(this, Observer { posts ->
            posts?.let { feedAdapter.updatePosts(it) }
        })
    }
}