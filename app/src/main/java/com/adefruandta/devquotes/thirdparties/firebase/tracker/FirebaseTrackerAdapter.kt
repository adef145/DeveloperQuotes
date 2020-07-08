package com.adefruandta.devquotes.thirdparties.firebase.tracker

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.happyfresh.happytracker.Adapter
import com.happyfresh.happytracker.Properties

class FirebaseTrackerAdapter : Adapter() {

    private val firebaseAnalytics: FirebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(context!!)
    }

    override fun onEvent(event: String?, properties: Properties?) {
        super.onEvent(event, properties)

        val bundle = Bundle()
        properties?.data?.entries?.forEach {
            bundle.putString(it.key, it.value.toString())
        }
        event?.also {
            firebaseAnalytics.logEvent(it, bundle)
        }
    }
}