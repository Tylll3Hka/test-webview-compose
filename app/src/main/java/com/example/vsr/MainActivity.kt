package com.example.vsr

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()

        setContent {
            rememberSystemUiController().setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = true
            )
            WebViewCommunication(
                modifier = Modifier
                    .systemBarsPadding()
            )
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewCommunication(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        AndroidView(factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)

                addJavascriptInterface(JavaScriptInterface(context), "AndroidFunction")
                loadUrl("file:///android_asset/index.html")
            }
        })
    }
}

class JavaScriptInterface(private val context: Context) {
    @JavascriptInterface
    fun showToast(str: String) = Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
}