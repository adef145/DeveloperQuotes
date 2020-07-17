package com.adefruandta.devquotes.domain.usecase

import com.adefruandta.devquotes.domain.Preferences
import com.adefruandta.devquotes.domain.database.QuoteDao
import com.adefruandta.devquotes.domain.service.QuoteService
import com.adefruandta.devquotes.model.Quote
import com.happyfresh.usecase.UseCase
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class GetQuoteUseCase(
    private val preferences: Preferences,
    private val quoteDao: QuoteDao,
    private val quoteService: QuoteService
) : UseCase<Quote>() {

    override fun observe(): Observable<Quote> {
        val lastQuoteLocal = (preferences.quoteId?.let {
            quoteDao.get(it).toObservable()
        } ?: Observable.empty())
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .onErrorResumeNext(Observable.empty())

        val randomQuoteLocal = quoteDao.random()
            .toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .onErrorResumeNext(lastQuoteLocal)

        return quoteService.random()
            .flatMap {
                quoteDao.get(it.id)
                    .toObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .onErrorReturnItem(it)
            }.flatMap { quote ->
                preferences.quoteId = quote.id
                quoteDao.insert(quote)
                    .toSingleDefault(1)
                    .toObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .concatMap { Observable.just(quote) }
                    .onErrorReturnItem(quote)
            }.observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .onErrorResumeNext(randomQuoteLocal)
    }
}