package com.example.echojournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.echojournal.ui.theme.EchoJournalTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.echojournal.ui.screens.historyscreen.JournalHistoryScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            EchoJournalTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    EchoJournalApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun EchoJournalApp(modifier: Modifier) {
    NavigationRoot()
}

