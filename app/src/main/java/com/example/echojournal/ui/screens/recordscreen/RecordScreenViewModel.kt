package com.example.echojournal.ui.screens.recordscreen

import androidx.lifecycle.ViewModel
import com.example.echojournal.audioplayback.AudioPlayer
import com.example.echojournal.audiorecorder.AudioRecorder
import com.example.echojournal.data.AudioRepository
import java.io.File
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecordingViewModel @Inject constructor(
    private val recorder: AudioRecorder,
    private val player: AudioPlayer,
    private val audioRepository: AudioRepository
) : ViewModel() {

    var isRecording: Boolean = false
        private set
    var isPaused: Boolean = false
        private set

    private var currentOutputFile: File? = null

    /**
     * Starts recording by creating a new file and initiating the recorder.
     * @param name The base name for the recording file.
     */
    fun startRecording(name: String) {
        val file = audioRepository.createRecordingFile(name)
        currentOutputFile = file
        recorder.start(file)
        isRecording = true
        isPaused = false
    }

    /**
     * Pauses the ongoing recording.
     */
    fun pauseRecording() {
        if (isRecording && !isPaused) {
            recorder.pause()
            isPaused = true
        }
    }

    /**
     * Resumes a paused recording.
     */
    fun resumeRecording() {
        if (isRecording && isPaused) {
            recorder.resume()
            isPaused = false
        }
    }

    /**
     * Stops the recording and returns the file path if successful.
     * @return The absolute path of the recorded file or null if failed.
     */
    fun stopRecording(): String? {
        return try {
            recorder.stop()
            isRecording = false
            isPaused = false
            currentOutputFile?.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Cancels the recording and deletes the partial file.
     */
    fun cancelRecording() {
        recorder.cancel()
        isRecording = false
        isPaused = false
        currentOutputFile = null
    }

    /**
     * Plays the specified audio file.
     * @param file The File to play.
     */
    fun playRecording(file: File) {
        player.playFile(file)
    }

    /**
     * Stops any ongoing playback.
     */
    fun stopPlayback() {
        player.stop()
    }

    /**
     * Retrieves all recorded audio files.
     * @return List of audio Files.
     */
    fun getAllRecordings(): List<File> {
        return audioRepository.getAllAudioFiles()
    }
}

