package com.example.navigationdrawer

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import java.util.Locale

class speach_to_text : AppCompatActivity() {


    private val REQUEST_CODE_SPEECh_INPUT = 100
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speach_to_text)
        val voiceBtn1 = findViewById<ImageButton>(R.id.voiceBtn1)
        val textTv = findViewById<TextView>(R.id.textTv) // Uncomment this line

        voiceBtn1.setOnClickListener {
            speak()
        }
    }

    private fun speak() {
        // intent to show speechToText dialog
        val mIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        mIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi Speak Something")

        try {
            // if there is no error show speechTOtEXT DIALOG
            startActivityForResult(mIntent, REQUEST_CODE_SPEECh_INPUT)
        } catch (e: Exception) {
            // if there is any error get error and show in toast
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_SPEECh_INPUT -> {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    // get text from result
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    // set the text to textview
                    findViewById<TextView>(R.id.textTv).text = result?.get(0) ?: "No result"
                }
            }
        }
    }
}