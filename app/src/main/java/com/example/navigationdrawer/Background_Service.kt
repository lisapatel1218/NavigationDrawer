package com.example.navigationdrawer
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class Background_Service : AppCompatActivity(), View.OnClickListener {
    // Button objects
    private lateinit var buttonStart: Button
    private lateinit var buttonStop: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background_service)

        // Getting buttons from XML
        buttonStart = findViewById<Button>(R.id.buttonStart)
        buttonStop = findViewById<Button>(R.id.buttonStop)

        // Attaching OnClickListener to buttons
        buttonStart.setOnClickListener(this)
        buttonStop.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view) {
            buttonStart -> startService(Intent(this, MyService::class.java))
            buttonStop -> stopService(Intent(this, MyService::class.java))
        }
    }
}