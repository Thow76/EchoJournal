package com.example.echojournal.ui.components

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.echojournal.R
import com.example.echojournal.viewmodels.RecordingViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun StartRecordingFAB(
    coroutineScope: CoroutineScope,
    recordAudioPermissionState: PermissionState,
    isRecordingRequested: MutableState<Boolean>,
    showRecordSheet: MutableState<Boolean>,
    recordingViewModel: RecordingViewModel
) {
    CustomGradientIconButton(
        onClick = {
            coroutineScope.launch {
                if (recordAudioPermissionState.status != PermissionStatus.Granted) {
                    // User has initiated a recording request
                    Log.d("JournalHistoryScreen", "Requesting recording permission.")
                    isRecordingRequested.value = true
                    recordAudioPermissionState.launchPermissionRequest()
                } else {
                    // Permission already granted; start recording
                    Log.d("JournalHistoryScreen", "Permission already granted. Starting recording.")
                    showRecordSheet.value = true
                    recordingViewModel.startRecording()
                }
            }
        },
        icon = {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.content_description_record),
                tint = Color.White
            )
        },
        contentDescription = stringResource(R.string.content_description_record_a_new_entry)
    )
}
