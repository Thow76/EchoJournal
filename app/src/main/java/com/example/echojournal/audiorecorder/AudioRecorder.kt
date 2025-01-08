package com.example.echojournal.audiorecorder

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun pause()
    fun resume()
    fun stop()
    fun cancel()

}