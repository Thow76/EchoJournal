package com.example.echojournal

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.echojournal.ui.screens.historyscreen.JournalHistoryScreen
import com.example.echojournal.ui.screens.historyscreen.JournalHistoryViewModel

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home") {

        composable("home") { JournalHistoryScreen(navController, viewModel = JournalHistoryViewModel()) }

    }
}