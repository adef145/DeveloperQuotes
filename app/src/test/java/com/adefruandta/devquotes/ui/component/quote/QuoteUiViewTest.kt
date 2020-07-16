package com.adefruandta.devquotes.ui.component.quote

import android.text.Html
import android.text.Spanned
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.adefruandta.devquotes.BaseTest
import com.adefruandta.devquotes.model.Quote
import com.adefruandta.devquotes.ui.event.IntentRefreshQuote
import com.adefruandta.devquotes.ui.event.IntentShareQuote
import com.happyfresh.happyarch.EventObservable
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Test

class QuoteUiViewTest : BaseTest() {

    @MockK
    lateinit var view: View

    @MockK
    lateinit var eventObservable: EventObservable

    @MockK
    lateinit var quoteTextView: TextView

    @MockK
    lateinit var shareImageButton: ImageButton

    @MockK
    lateinit var refreshImageButton: ImageButton

    @MockK
    lateinit var progressView: View

    val quoteString = "<![CDATA[\"%1s\"<br/><br/>by <b>%2s</b>]]>"

    lateinit var uiView: QuoteUiView

    override fun before() {
        super.before()

        uiView = spyk(QuoteUiView(view, eventObservable))

        every { uiView.quoteTextView } returns quoteTextView
        every { uiView.shareImageButton } returns shareImageButton
        every { uiView.refreshImageButton } returns refreshImageButton
        every { uiView.progressView } returns progressView
        every { uiView.quoteString } returns quoteString
    }

    @Test
    fun setup_test() {
        val shareImageButtonOnClickSlot = slot<View.OnClickListener>()
        val refreshImageButtonOnClickSlot = slot<View.OnClickListener>()

        uiView.setup()

        verify {
            shareImageButton.setOnClickListener(capture(shareImageButtonOnClickSlot))
            refreshImageButton.setOnClickListener(capture(refreshImageButtonOnClickSlot))
        }

        every { quoteTextView.text } returns "\"Quote\" by Author"
        shareImageButtonOnClickSlot.captured.onClick(view)

        val intentShareQuoteSlot = slot<IntentShareQuote>()
        verify { eventObservable.emit(QuoteComponent::class.java, capture(intentShareQuoteSlot)) }
        assert("\"Quote\" by Author" == intentShareQuoteSlot.captured.quote)

        refreshImageButtonOnClickSlot.captured.onClick(view)

        verify { eventObservable.emit(QuoteComponent::class.java, any<IntentRefreshQuote>()) }
    }

    @Test
    fun showQuote_test() {
        val quote = Quote().apply {
            en = "Quote"
            author = "Author"
        }

        val spanned = mockk<Spanned>()
        mockkStatic(Html::class)
        every { Html.fromHtml("<![CDATA[\"Quote\"<br/><br/>by <b>Author</b>]]>") } returns spanned

        uiView.showQuote(quote)

        val spannedSlot = slot<Spanned>()
        verify {
            quoteTextView.text = spanned
            progressView.visibility = View.GONE
            shareImageButton.visibility = View.VISIBLE
            refreshImageButton.visibility = View.VISIBLE
        }
    }

    @Test
    fun showProgress_test() {
        uiView.showProgress()

        verify {
            progressView.visibility = View.VISIBLE
            shareImageButton.visibility = View.INVISIBLE
            refreshImageButton.visibility = View.INVISIBLE
        }
    }
}