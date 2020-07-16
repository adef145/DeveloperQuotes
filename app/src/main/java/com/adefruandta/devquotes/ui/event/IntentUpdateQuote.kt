package com.adefruandta.devquotes.ui.event

import com.adefruandta.devquotes.model.Quote
import com.happyfresh.happyarch.Event

data class IntentUpdateQuote(
    val quote: Quote
) : Event