package com.will.capstonebangkit.ui.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.will.capstonebangkit.R

class NewsWebViewActivity : AppCompatActivity() {

    lateinit var webView: WebView
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_news_web_view)

        webView = findViewById(R.id.webView)

        val newsUrl = intent.getStringExtra("NEWS_URL")

        if (newsUrl != null) {
            webView.loadUrl(newsUrl)
        } else {
            Toast.makeText(this, "News URL is null", Toast.LENGTH_SHORT).show()
            finish()
        }
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)

        webView.webViewClient = object : WebViewClient(){}

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    if (isEnabled) {
                        isEnabled = false
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        })
    }
}

