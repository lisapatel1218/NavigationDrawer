package com.example.navigationdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.navigationdrawer.databinding.ActivityFooterbar2Binding

class footerbar2 : AppCompatActivity() {
    private lateinit var binding: ActivityFooterbar2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFooterbar2Binding.inflate(layoutInflater)
        setContentView(binding.root)  // Use binding.root instead of R.layout.activity_footerbar2

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(Home())
                    true
                }
                R.id.profile -> {
                    replaceFragment(Profile())
                    true
                }
                R.id.setting -> {
                    replaceFragment(Setting())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}

