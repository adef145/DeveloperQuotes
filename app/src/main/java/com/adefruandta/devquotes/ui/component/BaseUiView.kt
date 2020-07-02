package com.adefruandta.devquotes.ui.component

import android.view.View
import android.widget.Toast
import com.happyfresh.happyarch.EventObservable
import com.happyfresh.happyarch.UiView

open class BaseUiView(view: View, eventObservable: EventObservable) :
    UiView(view, eventObservable) {

    fun hide() {
        view.visibility = View.GONE
    }

    fun show() {
        view.visibility = View.VISIBLE
    }

    fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}