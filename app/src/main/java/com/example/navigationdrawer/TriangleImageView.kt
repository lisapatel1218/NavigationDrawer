package com.example.navigationdrawer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class TriangleImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val clipPath = Path()

    override fun onDraw(canvas: Canvas) {
        val w = width
        val h = height

        clipPath.reset()
        clipPath.moveTo(0f, h.toFloat()) // Bottom left
        clipPath.lineTo(w / 2f, 0f) // Top center
        clipPath.lineTo(w.toFloat(), h.toFloat()) // Bottom right
        clipPath.close()

        canvas.clipPath(clipPath)
        super.onDraw(canvas)
    }
}