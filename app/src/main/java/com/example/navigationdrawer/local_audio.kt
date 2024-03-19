package com.example.navigationdrawer

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class local_audio : AppCompatActivity() {

    private val PICK_AUDIO_REQUEST = 1
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_audio)

        val selectAudioButton: Button = findViewById(R.id.playButton)
        selectAudioButton.setOnClickListener {
            openGalleryForAudio()
        }
    }

    private fun openGalleryForAudio() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "audio/*"
        startActivityForResult(intent, PICK_AUDIO_REQUEST)
    }

    private fun playAudio(selectedAudioUri: Uri) {
        mediaPlayer?.reset()

        try {
            mediaPlayer?.setDataSource(this, selectedAudioUri)
            mediaPlayer?.prepare()
            mediaPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_AUDIO_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedAudioUri: Uri? = data.data

            if (selectedAudioUri != null) {
                playAudio(selectedAudioUri)
            }
        }
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }
}