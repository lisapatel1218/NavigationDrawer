package com.example.navigationdrawer
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings
class MyService : Service() {
    private var player: MediaPlayer? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // Getting system's default ringtone
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)
        // Setting loop play to true
        // This will make the ringtone continuously playing
        player?.isLooping = true

        // Starting the player
        player?.start()

        // We have some options for the service
        // Start sticky means service will be explicitly started and stopped
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stopping the player when service is destroyed
        player?.stop()
    }
}
