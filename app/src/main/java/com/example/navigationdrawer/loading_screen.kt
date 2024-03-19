package com.example.navigationdrawer


import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.navigationdrawer.databinding.ActivityLoadingScreenBinding
import com.facebook.shimmer.ShimmerFrameLayout

import com.example.navigationdrawer.databinding.ActivityMainBinding

class loading_screen : AppCompatActivity() {

    private lateinit var binding: ActivityLoadingScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dataMainlayout.visibility = View.GONE
        binding.shimmerView.visibility = View.VISIBLE
        binding.shimmerView.startShimmer()

        Handler().postDelayed({
            binding.dataMainlayout.visibility = View.VISIBLE
            binding.shimmerView.stopShimmer()
            binding.shimmerView.visibility = View.GONE
        }, 10000)
    }
}