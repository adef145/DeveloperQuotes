package com.adefruandta.devquotes.domain.usecase

import com.adefruandta.devquotes.BaseTest
import com.adefruandta.devquotes.domain.Preferences
import com.adefruandta.devquotes.domain.database.QuoteDao
import com.adefruandta.devquotes.domain.service.QuoteService
import com.adefruandta.devquotes.model.Quote
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Test

class GetQuoteUseCaseTest : BaseTest() {

    @MockK
    lateinit var preferences: Preferences

    @MockK
    lateinit var quoteDao: QuoteDao

    @MockK
    lateinit var quoteService: QuoteService

    lateinit var useCase: GetQuoteUseCase

    override fun before() {
        super.before()
        useCase = GetQuoteUseCase(preferences, quoteDao, quoteService)
    }

    @Test
    fun observe_test() {
        val quote = Quote()
        every { preferences.quoteId } returns "Quote Id"
        every { quoteDao.get("Quote Id") } returns Single.just(quote)
        every { quoteService.random() } returns Observable.just(quote)

        val testObserver = useCase.observe().test()

        testObserver.assertComplete()
        testObserver.assertValues(quote, quote)
    }

    @Test
    fun observe_emptyLocal_test() {
        val quote = Quote()
        every { preferences.quoteId } returns null
        every { quoteService.random() } returns Observable.just(quote)

        val testObserver = useCase.observe().test()

        testObserver.assertComplete()
        testObserver.assertValues(quote)
    }
}