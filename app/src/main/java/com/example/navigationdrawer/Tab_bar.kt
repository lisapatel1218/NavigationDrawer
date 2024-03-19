package com.example.navigationdrawer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.navigationdrawer.Adapter.MyAdapter
import com.google.android.material.tabs.TabLayout
import java.text.FieldPosition

class Tab_bar : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_bar)

        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.viewpager)


        tabLayout.addTab(tabLayout.newTab().setText("Tab1"))
        tabLayout.addTab(tabLayout.newTab().setText("Tab2"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter =MyAdapter(this, supportFragmentManager,tabLayout.tabCount)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }
}