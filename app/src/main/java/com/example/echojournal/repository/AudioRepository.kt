package com.example.echojournal.repository

import android.content.Context
import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioRepository @Inject constructor(
    private val context: Context
) {

    private val directory: File = context.cacheDir
    private val TAG = "AudioRepository"

    /**
     * Creates a new audio file with a unique name in the cache directory.
     * @return The created File object.
     */
    fun createRecordingFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.UK).format(Date())
        val fileName = "AUDIO_${timeStamp}.m4a"
        val newFile = File(directory, fileName)
        Log.d(TAG, "Creating new audio file: ${newFile.absolutePath}")
        return newFile
    }

    /**
     * Deletes the specified audio file from the cache directory.
     * @param file The File to delete.
     */
    fun deleteAudioFile(file: File) {
        if (file.exists()) {
            val deleted = file.delete()
            if (deleted) {
                Log.d(TAG, "Deleted audio file: ${file.absolutePath}")
            } else {
                Log.e(TAG, "Failed to delete audio file: ${file.absolutePath}")
            }
        } else {
            Log.w(TAG, "Attempted to delete a non-existing file: ${file.absolutePath}")
        }
    }


    /**
     * Retrieves all audio files from the cache directory.
     * @return A list of audio Files.
     */
    fun getAllAudioFiles(): List<File> {
        val audioFiles = directory.listFiles { file -> file.extension == "m4a" }?.toList() ?: emptyList()
        Log.d(TAG, "Retrieved ${audioFiles.size} audio files from cache directory.")
        return audioFiles
    }
}





