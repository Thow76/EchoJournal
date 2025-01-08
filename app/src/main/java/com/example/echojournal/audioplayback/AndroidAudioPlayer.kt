package com.example.echojournal.audioplayback

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidAudioPlayer @Inject constructor(
    private val context: Context
) : AudioPlayer {

    private var player: MediaPlayer? = null

    override fun playFile(file: File) {
        stop() // Ensure any existing playback is stopped

        player = MediaPlayer().apply {
            try {
                setDataSource(context, Uri.fromFile(file))
                prepare()
                start()
                Log.d("AndroidAudioPlayer", "Playing: ${file.absolutePath}")
            } catch (e: Exception) {
                e.printStackTrace()
                release()
            }
        }
    }

    override fun stop() {
        player?.run {
            if (isPlaying) {
                stop()
                Log.d("AndroidAudioPlayer", "Playback stopped.")
            }
            release()
        }
        player = null
    }
}

