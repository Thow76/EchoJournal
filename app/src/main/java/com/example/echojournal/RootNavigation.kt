package com.example.echojournal

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.echojournal.ui.screens.historyscreen.JournalHistoryScreen
import com.example.echojournal.ui.screens.createentryscreen.CreateRecordScreen

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home") {

        composable("home") { JournalHistoryScreen(navController) }
        composable("createEntry?filePath={filePath}") { backStackEntry ->
            val filePath = backStackEntry.arguments?.getString("filePath")
            CreateRecordScreen(navController, audioFilePath = filePath)
        }


    }
}