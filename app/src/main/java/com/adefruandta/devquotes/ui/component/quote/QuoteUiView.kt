package com.adefruandta.devquotes.ui.component.quote

import android.text.Html
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import com.adefruandta.devquotes.R
import com.adefruandta.devquotes.model.Quote
import com.adefruandta.devquotes.ui.component.BaseUiView
import com.adefruandta.devquotes.ui.event.IntentUpdateQuote
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

    val favouriteCheckBox: AppCompatCheckBox by lazy {
        view.findViewById<AppCompatCheckBox>(R.id.ui_view_quote_favourite_button)
    }

    val quoteString: String by lazy {
        context.getString(R.string.quote)
    }

    lateinit var quote: Quote

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
        favouriteCheckBox.setOnCheckedChangeListener { _, isChecked ->
            eventObservable.emit(
                QuoteComponent::class.java,
                IntentUpdateQuote(quote.apply {
                    favourite = isChecked
                })
            )
        }
    }

    fun showQuote(quote: Quote) {
        this.quote = quote
        quoteTextView.text = Html.fromHtml(String.format(quoteString, quote.text(), quote.author))
        progressView.visibility = View.GONE
        shareImageButton.visibility = View.VISIBLE
        refreshImageButton.visibility = View.VISIBLE
        favouriteCheckBox.visibility = View.VISIBLE
        favouriteCheckBox.isChecked = quote.favourite
    }

    fun showProgress() {
        progressView.visibility = View.VISIBLE
        shareImageButton.visibility = View.INVISIBLE
        refreshImageButton.visibility = View.INVISIBLE
        favouriteCheckBox.visibility = View.INVISIBLE
    }
}