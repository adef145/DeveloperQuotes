package com.adefruandta.devquotes.domain.service

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier

object Service {

    private val retrofit: Retrofit by lazy {
        val gson = GsonBuilder()
            .excludeFieldsWithModifiers(
                Modifier.FINAL,
                Modifier.TRANSIENT,
                Modifier.STATIC
            ).create()

        Retrofit.Builder()
            .baseUrl("https://programming-quotes-api.herokuapp.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    val quoteService: QuoteService by lazy {
        retrofit.create(QuoteService::class.java)
    }
}