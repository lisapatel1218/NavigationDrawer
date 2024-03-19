package com.example.navigationdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.ImageReader
import android.media.MediaScannerConnection
import android.net.Uri
import android.provider.MediaStore
import android.util.Size
import android.util.SparseIntArray
import android.view.Surface
import android.view.TextureView
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream

// ... (other imports)

class camera : AppCompatActivity() {
    private lateinit var textureView: TextureView
    private lateinit var cameraDevice: CameraDevice
    private lateinit var cameraCaptureSession: CameraCaptureSession
    private lateinit var captureRequestBuilder: CaptureRequest.Builder
    private lateinit var imageReader: ImageReader
    private lateinit var file: File

    private var cameraId: String = ""
    private var isFrontCamera = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        textureView = findViewById(R.id.textureView)
        val galleryButton: ImageButton = findViewById(R.id.gallery1)
        val captureButton: Button = findViewById(R.id.capture)
        val switchCameraButton: ImageButton = findViewById(R.id.switch_camera)

        textureView.surfaceTextureListener = surfaceTextureListener

        galleryButton.setOnClickListener { openGallery() }
        captureButton.setOnClickListener { capturePhoto() }
        switchCameraButton.setOnClickListener { switchCamera() }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        }
    }
    private val surfaceTextureListener = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
            openCamera()
        }

        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
            return true
        }

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
    }

    private fun openCamera() {
        val cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        try {
            cameraId = if (isFrontCamera) {
                cameraManager.cameraIdList.firstOrNull {
                    cameraManager.getCameraCharacteristics(it).get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT
                } ?: ""
            } else {
                cameraManager.cameraIdList.firstOrNull {
                    cameraManager.getCameraCharacteristics(it).get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK
                } ?: ""
            }

            if (cameraId.isNotEmpty() && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                cameraManager.openCamera(cameraId, object : CameraDevice.StateCallback() {
                    override fun onOpened(camera: CameraDevice) {
                        cameraDevice = camera
                        createCameraPreviewSession()
                    }

                    override fun onDisconnected(camera: CameraDevice) {
                        camera.close()
                    }

                    override fun onError(camera: CameraDevice, error: Int) {
                        camera.close()
                    }
                }, null)
            }
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun createCameraPreviewSession() {
        val texture = textureView.surfaceTexture
        texture?.setDefaultBufferSize(1920, 1080)
        val previewSurface = Surface(texture)

        // Prepare the ImageReader
        val imageReaderSize = Size(1920, 1080)
        imageReader = ImageReader.newInstance(imageReaderSize.width, imageReaderSize.height, ImageFormat.JPEG, 2).apply {
            setOnImageAvailableListener({ reader ->
                val image = reader.acquireNextImage()
                val buffer = image.planes[0].buffer
                val bytes = ByteArray(buffer.remaining())
                buffer.get(bytes)
                saveImage(bytes)
                image.close()
            }, null)
        }

        captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        captureRequestBuilder.addTarget(previewSurface)

        // List of Surfaces to be used in capture session
        val surfaces = ArrayList<Surface>().apply {
            add(previewSurface)
            add(imageReader.surface)
        }

        cameraDevice.createCaptureSession(surfaces, object : CameraCaptureSession.StateCallback() {
            override fun onConfigured(session: CameraCaptureSession) {
                if (cameraDevice == null) return
                cameraCaptureSession = session
                try {
                    captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
                    // Set other capture request settings as needed
                    cameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(), null, null)
                } catch (e: CameraAccessException) {
                    e.printStackTrace()
                }
            }

            override fun onConfigureFailed(session: CameraCaptureSession) {
                // showToast("Failed to configure camera.")
                Toast.makeText(this@camera, "Failed to configure camera.", Toast.LENGTH_SHORT).show()
            }
        }, null)
    }

    private fun saveImage(bytes: ByteArray) {
        file = File(externalMediaDirs.first(), "${System.currentTimeMillis()}.jpg")
        FileOutputStream(file).use {
            it.write(bytes)
        }
        // Scan the file to make it appear in the gallery immediately
        MediaScannerConnection.scanFile(this, arrayOf(file.toString()), null) { path, uri ->
            // You can perform an action here if needed when the scan is complete
        }
    }
    private fun capturePhoto() {
        val captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE).apply {
            addTarget(imageReader.surface)

            // Use AUTO mode for capture
            set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)

            // Calculate the rotation
            val rotation = windowManager.defaultDisplay.rotation
            set(CaptureRequest.JPEG_ORIENTATION, getOrientation(rotation))
        }

        cameraCaptureSession.stopRepeating()
        cameraCaptureSession.capture(captureBuilder.build(), object : CameraCaptureSession.CaptureCallback() {
            override fun onCaptureCompleted(session: CameraCaptureSession, request: CaptureRequest, result: TotalCaptureResult) {
                super.onCaptureCompleted(session, request, result)
                // Optionally, you can add logic here for after the image capture is completed
                createCameraPreviewSession() // Restart the preview
            }
        }, null)
    }

    private fun getCameraSensorOrientation(cameraId: String): Int {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val characteristics = cameraManager.getCameraCharacteristics(cameraId)
        return characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION) ?: 0
    }

    private fun getOrientation(rotation: Int): Int {
        val sensorOrientation = getCameraSensorOrientation(cameraId)
        val deviceOrientation = when (rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> 0
        }
        val totalRotation = if (isFrontCamera) {
            (360 + sensorOrientation + deviceOrientation) % 360
        } else {
            (sensorOrientation - deviceOrientation + 360) % 360
        }
        return totalRotation
    }



    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    private fun switchCamera() {
        isFrontCamera = !isFrontCamera
        cameraDevice.close()
        openCamera()
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 101
        private const val REQUEST_GALLERY = 102
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, open the camera
                    openCamera()
                } else {
                    // Permission denied, display a message or take appropriate action
                    Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }}