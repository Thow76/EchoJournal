package com.example.echojournal.di.audio

import com.example.echojournal.audioplayback.AndroidAudioPlayer
import com.example.echojournal.audioplayback.AudioPlayer
import com.example.echojournal.audiorecorder.AndroidAudioRecorder
import com.example.echojournal.audiorecorder.AudioRecorder

import javax.inject.Singleton
import android.content.Context
import com.example.echojournal.data.audio.AudioRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(SingletonComponent::class)
object AudioModule {

    @Provides
    @Singleton
    fun provideAudioRecorder(
        @ApplicationContext context: Context
    ): AudioRecorder {
        return AndroidAudioRecorder(context)
    }

    @Provides
    @Singleton
    fun provideAudioPlayer(
        @ApplicationContext context: Context
    ): AudioPlayer {
        return AndroidAudioPlayer(context)
    }

    @Provides
    @Singleton
    fun provideAudioRepository(
        @ApplicationContext context: Context
    ): AudioRepository {
        return AudioRepository(context)
    }
}

