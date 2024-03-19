package com.example.navigationdrawer.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.navigationdrawer.Fragment.first
import com.example.navigationdrawer.Fragment.second

internal class MyAdapter(var context: Context, fm: FragmentManager, var totalTabs: Int): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
    return when(position){
        0 ->{
            first()
        }
        1 ->{
            second()
        } else ->  getItem(position)
    }
    }
    override fun getCount(): Int {
        return totalTabs
    }




}
