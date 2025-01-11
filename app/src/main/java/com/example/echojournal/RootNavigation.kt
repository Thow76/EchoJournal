package com.example.echojournal

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.echojournal.ui.screens.historyscreen.JournalHistoryScreen
import com.example.echojournal.ui.screens.historyscreen.JournalHistoryViewModel
import com.example.echojournal.ui.screens.recordscreen.CreateEntryScreen

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home") {

        composable("home") { JournalHistoryScreen(navController) }
//        composable("createEntry") { CreateEntryScreen(navController) }
        composable("createEntry?filePath={filePath}") { backStackEntry ->
            val filePath = backStackEntry.arguments?.getString("filePath")
            CreateEntryScreen(navController, audioFilePath = filePath)
        }


    }
}