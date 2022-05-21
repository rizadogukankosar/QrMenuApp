package com.graifstudio.qrmenuapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MenuActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        //Data
        val intent = intent
        val menuLink: String? = intent.getStringExtra("link")


        webView = findViewById<WebView>(R.id.menu_webview)
        val refreshLayout: SwipeRefreshLayout = findViewById(R.id.refreshLayout)
        val webSettings: WebSettings = webView.getSettings()

        //Webview Settings
        webSettings.javaScriptEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webView.setWebViewClient(WebViewClient())
        webView.setVerticalScrollBarEnabled(false)

        if (checkInternetConnection())
            menuLink?.let { webView.loadUrl(it) }

        refreshLayout.setOnRefreshListener {
            if (checkInternetConnection())
                menuLink?.let { webView.loadUrl(it) }
            refreshLayout.isRefreshing = false
        }
    }

    private fun checkInternetConnection(): Boolean {
        var connection = false
        if (!DetectConnection.checkInternetConnection(applicationContext)) {
            connection = false
            if (webView.getUrl() == null || webView.getUrl() != "about:blank") {
                webView.loadDataWithBaseURL(
                    "file:///android_res/drawable/",
                    "<body style='background-color:#FFFFFF;'>" +
                            "<img src='app_icon.png' width='250px' height='250px' style='display: block; margin-left: auto; margin-right: auto; margin-top: 50%'/>" +
                            "<h2 style='font-size: 18px; font-weight: 200; text-align: center; color: orange ' >Please check your internet connection</h2>" +
                            "</body>",
                    "text/html", "utf-8",
                    null
                )
            }
        } else {
            connection = true
        }
        return connection
    }
}