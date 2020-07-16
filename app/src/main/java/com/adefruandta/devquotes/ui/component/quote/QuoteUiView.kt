package com.adefruandta.devquotes.ui.component.quote

import android.text.Html
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.adefruandta.devquotes.R
import com.adefruandta.devquotes.model.Quote
import com.adefruandta.devquotes.ui.component.BaseUiView
import com.adefruandta.devquotes.ui.event.IntentRefreshQuote
import com.adefruandta.devquotes.ui.event.IntentShareQuote
import com.happyfresh.happyarch.EventObservable

class QuoteUiView(view: View, eventObservable: EventObservable) :
    BaseUiView(view, eventObservable) {

    val quoteTextView: TextView by lazy {
        view.findViewById<TextView>(R.id.ui_view_quote_text_view)
    }

    val progressView: View by lazy {
        view.findViewById<View>(R.id.ui_view_quote_progress_view)
    }

    val shareImageButton: ImageButton by lazy {
        view.findViewById<ImageButton>(R.id.ui_view_quote_share_button)
    }

    val refreshImageButton: ImageButton by lazy {
        view.findViewById<ImageButton>(R.id.ui_view_quote_refresh_button)
    }

    val quoteString: String by lazy {
        context.getString(R.string.quote)
    }

    fun setup() {
        shareImageButton.setOnClickListener {
            eventObservable.emit(
                QuoteComponent::class.java,
                IntentShareQuote(quoteTextView.text.toString())
            )
        }
        refreshImageButton.setOnClickListener {
            eventObservable.emit(QuoteComponent::class.java, IntentRefreshQuote())
        }
    }

    fun showQuote(quote: Quote) {
        quoteTextView.text = Html.fromHtml(String.format(quoteString, quote.text(), quote.author))
        progressView.visibility = View.GONE
        shareImageButton.visibility = View.VISIBLE
        refreshImageButton.visibility = View.VISIBLE
    }

    fun showProgress() {
        progressView.visibility = View.VISIBLE
        shareImageButton.visibility = View.INVISIBLE
        refreshImageButton.visibility = View.INVISIBLE
    }
}