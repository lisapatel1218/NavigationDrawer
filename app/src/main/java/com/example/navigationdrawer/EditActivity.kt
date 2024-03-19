package com.example.navigationdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.navigationdrawer.db.UserDao

class EditActivity : AppCompatActivity() {


        private lateinit var userDao: UserDao
        private var userId: Long = -1 // Default value, update it with the actual user ID
        fun OnCreate(savedInstanceState: Bundle?) {
            // ... (existing code)

            val userId = intent.getLongExtra("USER_ID", -1)
            val userName = intent.getStringExtra("USER_NAME")
            val userPhone = intent.getStringExtra("USER_PHONE")
            val userEmail = intent.getStringExtra("USER_EMAIL")

            // Populate your UI fields with the user details for editing

        }
    }
