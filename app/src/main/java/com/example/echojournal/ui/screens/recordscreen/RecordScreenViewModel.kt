package com.example.echojournal.ui.screens.recordscreen

import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import java.io.File

class RecordingViewModel : ViewModel() {

    var isRecording: Boolean = false
        private set
    var isPaused: Boolean = false
        private set

    private var mediaRecorder: MediaRecorder? = null
    private var outputFile: File? = null

    /**
     * Start recording immediately:
     *  - Creates an output file (in external files dir, for example).
     *  - Prepares and starts MediaRecorder.
     */
    fun startRecording(outputFile: File) {
        this.outputFile = outputFile
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(outputFile.absolutePath)

            try {
                prepare()
                start()
                isRecording = true
                isPaused = false
                Log.d("RecordingViewModel", "Started recording to $outputFile")
            } catch (e: Exception) {
                e.printStackTrace()
                resetRecorder()
            }
        }
    }

    fun pauseRecording() {
        mediaRecorder?.pause()
        isPaused = true
        Log.d("RecordingViewModel", "Paused recording.")
    }

    fun resumeRecording() {
        mediaRecorder?.resume()
        isPaused = false
        Log.d("RecordingViewModel", "Resumed recording.")
    }

    /**
     * Stop and finalize the recording, returning the file path if successful.
     */
    fun stopRecording(): String? {
        return try {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            val filePath = outputFile?.absolutePath
            resetRecorder()
            filePath
        } catch (e: Exception) {
            e.printStackTrace()
            resetRecorder()
            null
        }
    }

    /**
     * Cancel the recording, discarding the file.
     */
    fun cancelRecording() {
        try {
            mediaRecorder?.stop()
            mediaRecorder?.release()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            // Delete partial file
            outputFile?.delete()
            resetRecorder()
        }
    }

    private fun resetRecorder() {
        mediaRecorder = null
        outputFile = null
        isRecording = false
        isPaused = false
    }
}
