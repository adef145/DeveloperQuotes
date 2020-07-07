package com.adefruandta.devquotes.thirdparties.firebase.tracker

import android.annotation.SuppressLint
import android.app.Application
import com.happyfresh.happytracker.Tracker
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions.emptyConsumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

object FirebaseTracker {

    private val subject = BehaviorSubject.create<FirebaseTrackerEvent>()

    fun init(application: Application) {
        init(Tracker.create(application, FirebaseTrackerEvent::class.java))
    }

    fun init(firebaseTrackerEvent: FirebaseTrackerEvent) {
        subject.onNext(firebaseTrackerEvent)
    }

    fun shareQuote(quote: String) {
        subscribe(Consumer {
            it.shareQuote(quote)
        })
    }

    fun refreshQuote() {
        subscribe(Consumer {
            it.refreshQuote()
        })
    }

    fun viewMainScreen(quote: String?) {
        subscribe(Consumer {
            it.viewMainScreen(quote)
        })
    }

    @SuppressLint("CheckResult")
    private fun subscribe(onNext: Consumer<FirebaseTrackerEvent>) {
        subject
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe(onNext, emptyConsumer())
    }
}