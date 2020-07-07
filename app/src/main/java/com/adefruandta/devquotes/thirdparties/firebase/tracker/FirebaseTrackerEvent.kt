package com.adefruandta.devquotes.thirdparties.firebase.tracker

import com.happyfresh.happytracker.annotations.Event
import com.happyfresh.happytracker.annotations.Property
import com.happyfresh.happytracker.annotations.Provider

@Provider(FirebaseTrackerAdapter::class)
interface FirebaseTrackerEvent {

    @Event("Share Quote")
    fun shareQuote(
        @Property("quote") quote: String
    )

    @Event("Refresh Quote")
    fun refreshQuote()

    @Event("View Main Screen")
    fun viewMainScreen(
        @Property("quote", optional = true) quote: String?
    )
}