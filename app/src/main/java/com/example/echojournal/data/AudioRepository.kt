package com.example.echojournal.data


import android.content.Context
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioRepository @Inject constructor(
    private val context: Context
) {

    private val directory: File = context.cacheDir

    /**
     * Creates a new audio file in the cache directory with the given name.
     * @param name The base name of the file (without extension).
     * @return The created File object.
     */
    fun createRecordingFile(name: String): File {
        val fileName = "$name.m4a"
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



