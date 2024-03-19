package com.example.navigationdrawer

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.soundcloud.android.crop.Crop
import java.io.File



class image_crop2 : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var btnSelectImage: Button
    private lateinit var shapeSpinner: Spinner
    companion object {

        private const val REQUEST_CODE_PERMISSIONS = 101
        private const val REQUEST_CODE_PICK_IMAGE = 102
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_crop2)
//        roundImageView = findViewById<>(R.id.imageView1)
        imageView = findViewById(R.id.imageView)
        btnSelectImage = findViewById(R.id.btnSelectImage)
        shapeSpinner = findViewById(R.id.spinner)
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val shapes = arrayOf("Square", "Round")

        if (!arePermissionsGranted(permissions)) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSIONS)
        }

        // Set up Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, shapes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        shapeSpinner.adapter = adapter

        // Set Spinner item selected listener
        shapeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                val selectedShape = shapes[position]
                applyShape(selectedShape)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Do nothing here
            }
        }


        // Set up Select Image button click listener
        btnSelectImage.setOnClickListener {
            pickImageFromGallery()
        }
    }


    private fun arePermissionsGranted(permissions: Array<String>): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }
    private fun applyShape(shape: String) {
        when (shape) {
            "Square" -> {
                imageView.setBackgroundResource(R.color.black)
                // Additional logic for square shape
            }
            "Round" -> {
                imageView.setBackgroundResource(android.R.color.transparent)
                // Additional logic for round shape
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (arePermissionsGranted(permissions)) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            val uri = data.data
            if (uri != null) {
                startCropActivity(uri)
            }
        } else if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            handleCropResult(data)
        }
    }

    private fun startCropActivity(uri: Uri) {
        Crop.of(uri, Uri.fromFile(File(cacheDir, "cropped")))
            .asSquare()
            .start(this)
    }

    private fun handleCropResult(data: Intent?) {
        val croppedUri = Crop.getOutput(data)
        if (croppedUri != null) {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, croppedUri)
            imageView.setImageBitmap(bitmap)
//            roundImageView.setImageBitmap(bitmap)
            saveImageToGallery(bitmap)
        }
    }


    private fun saveImageToGallery(bitmap: Bitmap) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "Image_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp")
        }

        val contentResolver = contentResolver
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        if (uri != null) {
            contentResolver.openOutputStream(uri).use { outputStream ->
                if (outputStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                    Toast.makeText(this, "Image saved to gallery", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to save image to gallery", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
