package com.adefruandta.devquotes.domain.usecase

import com.adefruandta.devquotes.BaseTest
import com.adefruandta.devquotes.domain.database.QuoteDao
import com.adefruandta.devquotes.model.Quote
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Completable
import org.junit.Test

class SaveQuoteUseCaseTest : BaseTest() {

    @MockK
    lateinit var quoteDao: QuoteDao

    @MockK
    lateinit var quote: Quote

    lateinit var useCase: SaveQuoteUseCase

    override fun before() {
        super.before()
        useCase = SaveQuoteUseCase(quoteDao, quote)
    }

    @Test
    fun observe_test() {
        every { quoteDao.insert(quote) } returns Completable.complete()

        val testObserver = useCase.observe().test()

        testObserver.assertComplete()
        testObserver.assertValue(1)
    }
}