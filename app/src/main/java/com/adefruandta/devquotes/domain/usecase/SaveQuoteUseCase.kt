package com.adefruandta.devquotes.domain.usecase

import com.adefruandta.devquotes.domain.database.QuoteDao
import com.adefruandta.devquotes.model.Quote
import com.happyfresh.usecase.UseCase
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class SaveQuoteUseCase(
    private val quoteDao: QuoteDao,
    private val quote: Quote
) : UseCase<Int>() {

    override fun observe(): Observable<Int> {
        return quoteDao.insert(quote).toSingleDefault(1).toObservable().subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }
}