package com.adefruandta.devquotes.ui.module.main

import android.content.Intent
import android.view.View
import android.view.ViewStub
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProvider
import com.adefruandta.devquotes.R
import com.adefruandta.devquotes.ui.component.quote.QuoteComponent
import com.adefruandta.devquotes.ui.event.IntentRefreshQuote
import com.adefruandta.devquotes.ui.event.IntentShareQuote
import com.adefruandta.devquotes.ui.module.BaseActivity
import com.happyfresh.happyarch.Subscribe

class MainActivity : BaseActivity() {

    override val layoutResId: Int = R.layout.activity_main

    val quoteComponentView: View by lazy {
        findViewById<ViewStub>(R.id.component_quote_stub).inflate()
    }

    val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
            MainViewModel::class.java
        )
    }

    override fun onInitiateComponent() {
        super.onInitiateComponent()
        componentProvider.add(QuoteComponent::class.java, quoteComponentView)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        mainViewModel.getQuote(eventObservable)
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
    }

    @Subscribe(QuoteComponent::class)
    fun intentRefreshQuote(intentRefreshQuote: IntentRefreshQuote) {
        mainViewModel.getQuote(eventObservable)
    }
}