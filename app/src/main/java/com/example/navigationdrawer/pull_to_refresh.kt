package com.example.navigationdrawer


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class pull_to_refresh : AppCompatActivity() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_to_refresh)
        swipeRefreshLayout = findViewById(R.id.swipeToRefresh)
        refreshApp()
    }
    private fun refreshApp() {
        swipeRefreshLayout.setOnRefreshListener {
            // Replace the following line with your actual refresh logic
            Toast.makeText(this, "Page Refreshed", Toast.LENGTH_SHORT).show()

            // Set refreshing to false to indicate that the refresh has finished
            swipeRefreshLayout.isRefreshing = false
        }
    }
}