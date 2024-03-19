package com.example.navigationdrawer

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView

class video_streaming : AppCompatActivity() {
    lateinit var videoView: VideoView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_streaming)
        videoView = findViewById(R.id.videoView);
        val videoUrl ="android.resource://" + getPackageName() + "/" + R.raw.on


        val uri: Uri = Uri.parse(videoUrl)


        videoView.setVideoURI(uri)


        val mediaController = MediaController(this)

        mediaController.setAnchorView(videoView)

        mediaController.setMediaPlayer(videoView)


        videoView.setMediaController(mediaController)


        videoView.start()

    }
}