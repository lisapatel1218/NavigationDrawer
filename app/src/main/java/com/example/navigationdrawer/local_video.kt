package com.example.navigationdrawer

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.MediaController
import android.widget.VideoView

class local_video : AppCompatActivity() {
    private val PICK_VIDEO_REQUEST = 1
    private lateinit var videoView: VideoView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_video)

        videoView = findViewById(R.id.videoView)
        val mediaController =
            MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        // Launch the gallery when the activity is created
        openGallery()
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_VIDEO_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_VIDEO_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedVideoUri: Uri? = data.data

            if (selectedVideoUri != null) {
                playVideo(selectedVideoUri)
            }
        }
    }

    private fun playVideo(videoUri: Uri) {
        videoView.setVideoURI(videoUri)
        videoView.start()
    }
}
