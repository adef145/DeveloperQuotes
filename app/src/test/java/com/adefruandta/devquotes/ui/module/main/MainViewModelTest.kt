package com.adefruandta.devquotes.ui.module.main

import android.app.Application
import com.adefruandta.devquotes.BaseTest
import com.adefruandta.devquotes.domain.Preferences
import com.adefruandta.devquotes.domain.database.QuoteDao
import com.adefruandta.devquotes.domain.service.QuoteService
import com.adefruandta.devquotes.domain.usecase.GetQuoteUseCase
import com.adefruandta.devquotes.model.Quote
import com.adefruandta.devquotes.ui.component.quote.QuoteComponent
import com.adefruandta.devquotes.ui.event.Error
import com.adefruandta.devquotes.ui.event.Loaded
import com.adefruandta.devquotes.ui.event.Loading
import com.happyfresh.happyarch.EventObservable
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import org.junit.Test

class MainViewModelTest : BaseTest() {

    @MockK
    lateinit var application: Application

    @MockK
    lateinit var quoteService: QuoteService

    @MockK
    lateinit var preferences: Preferences

    @MockK
    lateinit var quoteDao: QuoteDao

    @MockK
    lateinit var getQuoteDisposable: Disposable

    @MockK
    lateinit var eventObservable: EventObservable

    lateinit var viewModel: MainViewModel

    override fun before() {
        super.before()
        viewModel = spyk(MainViewModel(application))
        viewModel.getQuoteDisposable = getQuoteDisposable

        every { viewModel.preferences } returns preferences
        every { viewModel.quoteDao } returns quoteDao
        every { viewModel.quoteService } returns quoteService
    }

    @Test
    fun getQuote_onNext_test() {
        val quote = Quote()
        mockkConstructor(GetQuoteUseCase::class)
        every { anyConstructed<GetQuoteUseCase>().observe() } returns Observable.just(quote)

        viewModel.getQuote(eventObservable)

        val loadedSlot = slot<Loaded<Quote>>()
        verify {
            getQuoteDisposable.dispose()
            eventObservable.emit(QuoteComponent::class.java, any<Loading>())
            eventObservable.emit(QuoteComponent::class.java, capture(loadedSlot))
        }
        assert(quote == loadedSlot.captured.data)
    }

    @Test
    fun getQuote_onError_test() {
        val throwable = Throwable()
        mockkConstructor(GetQuoteUseCase::class)
        every { anyConstructed<GetQuoteUseCase>().observe() } returns Observable.error(throwable)

        viewModel.getQuote(eventObservable)

        val errorSlot = slot<Error>()
        verify {
            getQuoteDisposable.dispose()
            eventObservable.emit(QuoteComponent::class.java, any<Loading>())
            eventObservable.emit(QuoteComponent::class.java, capture(errorSlot))
        }
        assert(throwable == errorSlot.captured.throwable)
    }
}