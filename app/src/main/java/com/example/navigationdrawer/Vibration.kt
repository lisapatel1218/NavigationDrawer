package com.example.navigationdrawer

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.Button

class Vibration : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vibration)
        // Get reference to the vibrate button
        val vibrateButton: Button = findViewById(R.id.main_vibrateBtn)

        // Set a click listener on the button
        vibrateButton.setOnClickListener {
            vibrateDevice()
        }
    }

    // Function to vibrate the device
    private fun vibrateDevice() {
        // Get the Vibrator service
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Check if the device supports vibration
        if (vibrator.hasVibrator()) {
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                // Deprecated in API 26 (Oreo)
                vibrator.vibrate(500)
            }
        }
    }
}