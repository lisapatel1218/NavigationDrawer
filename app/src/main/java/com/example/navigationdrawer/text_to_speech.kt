package com.example.navigationdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.Locale

class text_to_speech : AppCompatActivity() {

    private lateinit var textToSpeech: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_to_speech)

        val editText = findViewById<EditText>(R.id.editText)
        val textToSpeachBtn = findViewById<Button>(R.id.textToSpeachBtn)

        textToSpeech = TextToSpeech(this){status->
            if (status == TextToSpeech.SUCCESS){
                val result = textToSpeech.setLanguage(Locale.getDefault())
                if(result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Toast.makeText(this, "language is not supported", Toast.LENGTH_SHORT).show()

                }
            }


        }
        textToSpeachBtn.setOnClickListener {
            if(editText.text.toString().trim().isNotEmpty()){
                textToSpeech.speak(editText.text.toString().trim(),TextToSpeech.QUEUE_FLUSH,
                null,
                    null

                )
            } else{
                Toast.makeText(this, "Required", Toast.LENGTH_SHORT).show()
            }
        }
    }
}