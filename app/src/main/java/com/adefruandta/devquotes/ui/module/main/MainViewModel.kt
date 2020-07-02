package com.adefruandta.devquotes.ui.module.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.adefruandta.devquotes.domain.Preferences
import com.adefruandta.devquotes.domain.database.Db
import com.adefruandta.devquotes.domain.database.QuoteDao
import com.adefruandta.devquotes.domain.service.QuoteService
import com.adefruandta.devquotes.domain.service.Service
import com.adefruandta.devquotes.domain.usecase.GetQuoteUseCase
import com.adefruandta.devquotes.ui.component.quote.QuoteComponent
import com.adefruandta.devquotes.ui.event.Error
import com.adefruandta.devquotes.ui.event.Loaded
import com.adefruandta.devquotes.ui.event.Loading
import com.happyfresh.happyarch.EventObservable
import io.reactivex.disposables.Disposable

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val quoteService: QuoteService by lazy {
        Service.quoteService
    }

    val quoteDao: QuoteDao by lazy {
        Db.getInstance(application).quoteDao()
    }

    val preferences: Preferences by lazy {
        Preferences.getInstance(application)
    }

    var getQuoteDisposable: Disposable? = null

    fun getQuote(eventObservable: EventObservable) {
        getQuoteDisposable?.dispose()
        eventObservable.emit(QuoteComponent::class.java, Loading())
        getQuoteDisposable =
            GetQuoteUseCase(preferences, quoteDao, quoteService).observe().subscribe(
                {
                    eventObservable.emit(QuoteComponent::class.java, Loaded(it))
                },
                {
                    eventObservable.emit(QuoteComponent::class.java, Error(it))
                }
            )
    }


}