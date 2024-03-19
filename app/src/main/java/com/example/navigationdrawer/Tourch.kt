package com.example.navigationdrawer

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Tourch : AppCompatActivity() {

    private lateinit var torchButton: ImageButton
    private lateinit var cameraManager: CameraManager
    private var isTorchOn: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tourch)


        torchButton = findViewById(R.id.torchButton)
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        torchButton.setOnClickListener {
            toggleTorch()
        }
    }

    private fun toggleTorch() {
        try {
            val cameraId = cameraManager.cameraIdList[0] // Use the first camera
            cameraManager.setTorchMode(cameraId, !isTorchOn)
            isTorchOn = !isTorchOn
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Make sure to turn off the torch when the activity is destroyed
        if (isTorchOn) {
            toggleTorch()
        }
    }
}
