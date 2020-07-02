package com.adefruandta.devquotes.domain.service

import com.adefruandta.devquotes.model.Quote
import io.reactivex.Observable
import retrofit2.http.GET


interface QuoteService {

    @GET("quotes/random")
    fun random(): Observable<Quote>
}