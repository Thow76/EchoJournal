package com.example.echojournal.repository

import android.content.Context
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

    /**
     * Creates a new audio file with a unique name in the cache directory.
     * @return The created File object.
     */
    fun createRecordingFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.UK).format(Date())
        val fileName = "AUDIO_${timeStamp}.m4a"
        return File(directory, fileName)
    }

    /**
     * Deletes the specified audio file from the cache directory.
     * @param file The File to delete.
     */
    fun deleteAudioFile(file: File) {
        if (file.exists()) {
            file.delete()
        }
    }

    /**
     * Retrieves all audio files from the cache directory.
     * @return A list of audio Files.
     */
    fun getAllAudioFiles(): List<File> {
        return directory.listFiles { file -> file.extension == "m4a" }?.toList() ?: emptyList()
    }
}




