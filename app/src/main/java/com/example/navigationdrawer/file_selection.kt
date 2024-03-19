package com.example.navigationdrawer

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class file_selection : AppCompatActivity() {
    private lateinit var btnSelectFile: Button
    private lateinit var tvFileName: TextView
    private lateinit var ivFilePreview: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_selection)
        btnSelectFile = findViewById(R.id.btnSelectFile)
        tvFileName = findViewById(R.id.tvFileName)
        ivFilePreview = findViewById(R.id.ivFilePreview)

        btnSelectFile.setOnClickListener {
            openFilePicker()
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, FILE_PICKER_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                // Handle the selected file
                displayFileName(uri)
                // Further processing of the file based on your application's needs
            }
        }
    }

    @SuppressLint("Range")
    private fun displayFileName(uri: Uri) {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                tvFileName.text = displayName
            }
        }
    }

    companion object {
        private const val FILE_PICKER_REQUEST_CODE = 1000
    }
}