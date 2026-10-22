package com.vectorstudio.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import com.vectorstudio.app.ui.editor.VectorStudioApp
import com.vectorstudio.app.ui.theme.VectorStudioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val darkTheme = isSystemInDarkTheme()
            VectorStudioTheme(darkTheme = darkTheme) {
                VectorStudioApp()
            }
        }
    }
}
