package com.adefruandta.devquotes.ui.component.quote

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.adefruandta.devquotes.BaseTest
import com.adefruandta.devquotes.model.Quote
import com.adefruandta.devquotes.ui.event.Error
import com.adefruandta.devquotes.ui.event.Loaded
import com.adefruandta.devquotes.ui.event.Loading
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test

class QuoteComponentTest : BaseTest() {

    @MockK
    lateinit var view: View

    @MockK
    lateinit var lifecycleOwner: LifecycleOwner

    @MockK
    lateinit var quoteUiView: QuoteUiView

    lateinit var component: QuoteComponent

    override fun before() {
        super.before()
        component = spyk(QuoteComponent(view, lifecycleOwner))

        every { component.uiView } returns quoteUiView
    }

    @Test
    fun onCreate_test() {
        component.onCreate()

        verify { quoteUiView.setup() }
    }

    @Test
    fun onLoading_test() {
        component.onLoading(Loading())

        verify {
            quoteUiView.showProgress()
            quoteUiView.show()
        }
    }

    @Test
    fun onError_test() {
        val throwable = Throwable("Message")
        component.onError(Error(throwable))

        verify {
            quoteUiView.showError("Message")
        }
    }

    @Test
    fun onLoaded_test() {
        val quote = Quote().apply {
            en = "Quote"
            author = "Author"
        }

        component.onLoaded(Loaded(quote))

        verify {
            quoteUiView.showQuote(quote)
            quoteUiView.show()
        }
    }
}