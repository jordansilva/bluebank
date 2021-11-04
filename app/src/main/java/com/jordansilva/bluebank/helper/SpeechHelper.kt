package com.jordansilva.bluebank.helper

import android.content.Context
import android.speech.tts.TextToSpeech
import java.lang.ref.WeakReference
import java.util.*

class SpeechHelper private constructor(context: WeakReference<Context>) : TextToSpeech.OnInitListener {

    private val tts: TextToSpeech = TextToSpeech(context.get(), this)

    override fun onInit(status: Int) {
        if (status == 1) TODO("Exception here")

        tts.language = Locale("pt", "BR")
    }

    fun speak(text: String) = tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "ContentDescriptionId")

    companion object {
        private lateinit var instance: SpeechHelper

        fun get(context: Context): SpeechHelper {
            if (!::instance.isInitialized) {
                synchronized(SpeechHelper::class) {
                    instance = SpeechHelper(WeakReference(context.applicationContext))
                }
            }
            return instance
        }
    }


}