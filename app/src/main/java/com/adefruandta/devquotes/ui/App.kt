package com.adefruandta.devquotes.ui

import android.app.Application
import com.adefruandta.devquotes.thirdparties.firebase.tracker.FirebaseTracker

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseTracker.init(this)
    }
}