package com.adefruandta.devquotes.ui.component.quote

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.adefruandta.devquotes.model.Quote
import com.adefruandta.devquotes.ui.event.Error
import com.adefruandta.devquotes.ui.event.Loaded
import com.adefruandta.devquotes.ui.event.Loading
import com.happyfresh.happyarch.Component
import com.happyfresh.happyarch.EventObservable
import com.happyfresh.happyarch.Subscribe

class QuoteComponent(view: View, lifecycleOwner: LifecycleOwner) :
    Component<QuoteUiView>(view, lifecycleOwner) {

    override fun onCreateView(view: View, eventObservable: EventObservable): QuoteUiView {
        return QuoteUiView(view, eventObservable)
    }

    override fun onCreate() {
        super.onCreate()
        uiView.setup()
    }

    @Subscribe(QuoteComponent::class)
    fun onLoading(loading: Loading) {
        uiView.showProgress()
        uiView.show()
    }

    @Subscribe(QuoteComponent::class)
    fun onError(error: Error) {
        error.throwable.message?.also {
            uiView.showError(it)
        }
    }

    @Subscribe(QuoteComponent::class)
    fun onLoaded(loaded: Loaded<Quote>) {
        uiView.showQuote(loaded.data)
        uiView.show()
    }
}