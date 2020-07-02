package com.adefruandta.devquotes.ui.event

import com.happyfresh.happyarch.Event

data class Loaded<T>(val data: T) : Event