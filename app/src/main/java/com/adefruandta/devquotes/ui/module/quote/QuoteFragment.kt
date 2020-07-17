package com.adefruandta.devquotes.ui.module.quote

import android.content.Intent
import android.view.View
import android.view.ViewStub
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProvider
import com.adefruandta.devquotes.R
import com.adefruandta.devquotes.model.Quote
import com.adefruandta.devquotes.thirdparties.firebase.tracker.FirebaseTracker
import com.adefruandta.devquotes.ui.component.quote.QuoteComponent
import com.adefruandta.devquotes.ui.event.IntentRefreshQuote
import com.adefruandta.devquotes.ui.event.IntentShareQuote
import com.adefruandta.devquotes.ui.event.IntentUpdateQuote
import com.adefruandta.devquotes.ui.event.Loaded
import com.adefruandta.devquotes.ui.module.BaseFragment
import com.happyfresh.happyarch.Subscribe

class QuoteFragment : BaseFragment() {

    override val layoutResId: Int = R.layout.fragment_quote

    var quote: String? = null

    val quoteComponentView: View by lazy {
        view?.findViewById<ViewStub>(R.id.component_quote_stub)?.inflate()
            ?: throw RuntimeException("View not found")
    }

    val quoteViewModel: QuoteViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(
                activity?.application ?: throw RuntimeException("Activity not found")
            )
        ).get(
            QuoteViewModel::class.java
        )
    }

    override fun onInitiateComponent() {
        super.onInitiateComponent()
        componentProvider.add(QuoteComponent::class.java, quoteComponentView)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onDidCreate() {
        quoteViewModel.getQuote(eventObservable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onDidResume() {
        FirebaseTracker.viewMainScreen(quote)
    }

    @Subscribe(QuoteComponent::class)
    fun onQuoteLoaded(loaded: Loaded<Quote>) {
        quote = loaded.data.text()
    }

    @Subscribe(QuoteComponent::class)
    fun intentShareQuote(intentShareQuote: IntentShareQuote) {
        val quote = intentShareQuote.quote
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, quote)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)

        FirebaseTracker.shareQuote(quote)
    }

    @Subscribe(QuoteComponent::class)
    fun intentRefreshQuote(intentRefreshQuote: IntentRefreshQuote) {
        quoteViewModel.getQuote(eventObservable)

        FirebaseTracker.refreshQuote()
    }

    @Subscribe(QuoteComponent::class)
    fun intentUpdateQuote(intentUpdateQuote: IntentUpdateQuote) {
        quoteViewModel.saveQuote(eventObservable, intentUpdateQuote.quote)
    }
}