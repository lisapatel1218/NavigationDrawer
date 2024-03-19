package com.example.navigationdrawer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class image_shape : AppCompatActivity() {

    private lateinit var regularImageView: ImageView
    private lateinit var triangleImageView: TriangleImageView
    private lateinit var spinnerShapes: Spinner
    private var imageUri: Uri? = null

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_shape)

        regularImageView = findViewById(R.id.regularImageView)
        triangleImageView = findViewById(R.id.triangleImageView)
        spinnerShapes = findViewById(R.id.spinnerShapes)

        spinnerShapes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedShape = parent.getItemAtPosition(position).toString()
                applyShapeToImage(selectedShape)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        val buttonPickImage: Button = findViewById(R.id.button_pick_image)
        buttonPickImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            imageUri = data?.data
            imageUri?.let { uri ->
                regularImageView.setImageURI(uri)
                applyShapeToImage(spinnerShapes.selectedItem.toString())
            }
        }
    }

    private fun applyShapeToImage(shape: String) {
        when (shape) {
            "Circle" -> {
                regularImageView.visibility = View.VISIBLE
                triangleImageView.visibility = View.GONE
                imageUri?.let { uri ->
                    Glide.with(this)
                        .load(uri)
                        .apply(RequestOptions.circleCropTransform())
                        .into(regularImageView)
                }
            }
            "Square" -> {
                regularImageView.visibility = View.VISIBLE
                triangleImageView.visibility = View.GONE
                imageUri?.let { uri ->
                    Glide.with(this)
                        .load(uri)
                        .apply(RequestOptions.centerInsideTransform()) // or .apply(RequestOptions.noTransformation())
                        .into(regularImageView)
                }
            }
            "Triangle" -> {
                regularImageView.visibility = View.GONE
                triangleImageView.visibility = View.VISIBLE
                imageUri?.let { uri ->
                    triangleImageView.setImageURI(uri)
                }
            }
        }
    }
}
