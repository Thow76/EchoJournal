package com.example.echojournal.audiorecorder

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidAudioRecorder @Inject constructor(
    @ApplicationContext private val context: Context
) : AudioRecorder {

    private var recorder: MediaRecorder? = null
    private var outputFile: File? = null

    private fun createRecorder(): MediaRecorder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }
    }

    override fun start(outputFile: File) {
        this.outputFile = outputFile
        recorder = createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(FileOutputStream(outputFile).fd)

            try {
                prepare()
                start()
                Log.d("AndroidAudioRecorder", "Recording started: ${outputFile.absolutePath}")
            } catch (e: Exception) {
                e.printStackTrace()
                resetRecorder()
            }
        }
    }

    override fun pause() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            recorder?.pause()
            Log.d("AndroidAudioRecorder", "Recording paused.")
        }
    }

    override fun resume() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            recorder?.resume()
            Log.d("AndroidAudioRecorder", "Recording resumed.")
        }
    }

    override fun stop() {
        recorder?.run {
            try {
                stop()
                Log.d("AndroidAudioRecorder", "Recording stopped.")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            release()
        }
        recorder = null
    }

    override fun cancel() {
        try {
            recorder?.stop()
            Log.d("AndroidAudioRecorder", "Recording canceled.")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            recorder?.release()
            recorder = null
            outputFile?.delete()
            Log.d("AndroidAudioRecorder", "Partial file deleted.")
        }
    }

    private fun resetRecorder() {
        recorder?.release()
        recorder = null
        outputFile = null
    }
}
