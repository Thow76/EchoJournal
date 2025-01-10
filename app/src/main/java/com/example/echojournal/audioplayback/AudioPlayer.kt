package com.example.echojournal.audioplayback

import java.io.File

interface AudioPlayer {
    fun playFile(file: File)
    fun stop()
    fun duration(): Long // Returns the total duration of the track in milliseconds
    fun getCurrentPosition(): Long // Returns the current playback position in milliseconds
    fun seekTo(position: Long) // Seeks to the specified position in milliseconds
}
