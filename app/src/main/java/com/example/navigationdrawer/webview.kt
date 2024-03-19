package com.example.navigationdrawer

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class webview : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        // Initialize the WebView
        webView = findViewById(R.id.web)

        // Load the initial URL
        webView.loadUrl("https://www.google.com")

        // Enable JavaScript
        webView.settings.javaScriptEnabled = true

        // Set a WebViewClient to handle links within the WebView
        webView.webViewClient = WebViewClient()

        // Enable support for zooming
        webView.settings.setSupportZoom(true)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
