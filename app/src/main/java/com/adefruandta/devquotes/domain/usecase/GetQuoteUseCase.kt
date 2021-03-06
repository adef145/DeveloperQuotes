package com.adefruandta.devquotes.domain.usecase

import com.adefruandta.devquotes.domain.Preferences
import com.adefruandta.devquotes.domain.database.QuoteDao
import com.adefruandta.devquotes.domain.service.QuoteService
import com.adefruandta.devquotes.model.Quote
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class GetQuoteUseCase(
    private val preferences: Preferences,
    private val quoteDao: QuoteDao,
    private val quoteService: QuoteService
) : UseCase<Quote>() {

    override fun observe(): Observable<Quote> {
        val local = (preferences.quoteId?.let {
            quoteDao.get(it).toObservable()
        } ?: Observable.empty())
            .onErrorResumeNext(Observable.empty())
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())

        val remote = quoteService.random().flatMap {
            quoteDao.insert(it)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe()
            preferences.quoteId = it.id
            Observable.just(it)
        }

        return Observable.concat(local, remote)
    }
}