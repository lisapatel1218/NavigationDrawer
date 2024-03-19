package com.example.navigationdrawer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class floating_button3 : AppCompatActivity() {

    private lateinit var add_btn: FloatingActionButton
    private lateinit var pen_btn: FloatingActionButton
    private lateinit var img_btn: FloatingActionButton

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.form_bottom_anim)}
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim)}

    private var clicked = false
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_floating_button3)

        add_btn = findViewById(R.id.add_btn)
        pen_btn = findViewById(R.id.pen_btn)
        img_btn = findViewById(R.id.img_btn)

        add_btn.setOnClickListener{
            onAddButtonClicked()
        }
        pen_btn.setOnClickListener{
            Toast.makeText(this, "Edit Button Clicked", Toast.LENGTH_SHORT).show()
        }
        img_btn.setOnClickListener{
            Toast.makeText(this, "Image Button Clicked", Toast.LENGTH_SHORT).show()
        }





    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked : Boolean) {
        if(!clicked)
        {
            pen_btn.visibility = View.VISIBLE
            img_btn.visibility = View.VISIBLE
        }
        else{
            pen_btn.visibility = View.INVISIBLE
            img_btn.visibility = View.INVISIBLE
        }
    }
    private fun setAnimation(clicked : Boolean) {
        if(!clicked)
        {
            pen_btn.startAnimation(fromBottom)
            img_btn.startAnimation(fromBottom)
            add_btn.startAnimation(rotateOpen)
        }
        else{
            pen_btn.startAnimation(toBottom)
            img_btn.startAnimation(toBottom)
            add_btn.startAnimation(rotateClose)

        }
    }

    private fun setClickable(clicked: Boolean)
    {
        if(!clicked)
        {
            pen_btn.isClickable=false
            img_btn.isClickable=false
        }
        else
        {
            pen_btn.isClickable=true
            img_btn.isClickable=true
        }
    }
}

