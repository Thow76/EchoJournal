package com.example.echojournal.ui.screens.historyscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.echojournal.data.JournalEntry
import com.example.echojournal.R
import com.example.echojournal.ui.components.CustomAppBar
import com.example.echojournal.ui.components.ErrorSnackbar
import com.example.echojournal.ui.components.LoadingIndicator
import com.example.echojournal.ui.theme.Gradients
import com.example.echojournal.ui.theme.MoodColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalHistoryScreen(
    navController: NavController,
    viewModel: JournalHistoryViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Trigger loading of journal entries
    LaunchedEffect(Unit) {
        viewModel.loadJournalEntries()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Gradients.BgSaturateGradient)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CustomAppBar(title = stringResource(id = R.string.history_screen_heading))
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                FilterSection(
                    viewModel = viewModel
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    when {
                        uiState.isLoading -> {
                            LoadingIndicator()
                        }

                        uiState.errorMessage != null -> {
                            ErrorSnackbar(
                                modifier = Modifier.align(Alignment.BottomCenter),
                                errorMessage = "Error: ${uiState.errorMessage}",
                                onDismiss = viewModel::clearErrorMessage
                            )
                        }

                        uiState.journalEntries.isNotEmpty() -> {
                            JournalHistoryScreenList(journalEntries = uiState.journalEntries)
                        }

                        else -> {
                            JournalHistoryScreenEmpty(paddingValues)
                        }
                    }
                }
            }
        }
    }
}








