package com.example.navigationdrawer

import android.annotation.SuppressLint
import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MyApp : Application(){
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // Declare the repository globally within the class
    lateinit var repository: PostRepository
        private set
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this) {}
        FirebaseApp.initializeApp(this)
        initializeData()
    }

        private fun initializeData() {
            val database = AppDatabase.getDatabase(this)
            val dao = database.postDao()
            repository = PostRepository(dao) // Initialize the repository

            applicationScope.launch {
                repository.insert(post(
                    userName = "Riddhi",
                    timeAgo = "Just now",
                    postImage = R.drawable.image_lisa1, // Use the resource ID
                    likesCount = 0,
                    commentsCount = 0,
                    isLiked = false
                ))
            }

        }
    }