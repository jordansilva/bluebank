package com.jordansilva.bluebank

import android.app.Application
import com.jordansilva.bluebank.helper.SpeechHelper

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SpeechHelper.get(this.applicationContext)
    }
}