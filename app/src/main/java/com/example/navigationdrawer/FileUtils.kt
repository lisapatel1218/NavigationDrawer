package com.example.navigationdrawer

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.io.IOException

object FileUtils {
    @Throws(IOException::class)
    fun getFile(context: Context, uri: Uri): File {
        val filePath = FileUtils.getPath(context, uri)
        return File(filePath!!)
    }

    private fun getPath(context: Context, uri: Uri): String? {
        var path: String? = null
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                path = it.getString(columnIndex)
            }
        }
        return path
    }
}   