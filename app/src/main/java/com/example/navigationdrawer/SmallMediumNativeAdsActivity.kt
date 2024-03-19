package com.example.navigationdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.navigationdrawer.mynative.TemplateView

class SmallMediumNativeAdsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_small_medium_native_ads)

        val templateView = findViewById<TemplateView>(R.id.templateView)
        loadSmallMediumSizeNativeAds(this,R.string.small_medium_native_ads,templateView)
    }
}