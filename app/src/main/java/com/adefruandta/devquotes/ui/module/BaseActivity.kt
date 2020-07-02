package com.adefruandta.devquotes.ui.module

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import com.happyfresh.happyarch.ComponentProvider
import com.happyfresh.happyarch.ComponentProviders
import com.happyfresh.happyarch.EventObservable

abstract class BaseActivity : AppCompatActivity(), LifecycleObserver {

    abstract val layoutResId: Int

    val eventObservable: EventObservable by lazy {
        EventObservable.get(this)
    }

    val componentProvider: ComponentProvider by lazy {
        ComponentProviders.of(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutResId)

        onInitiateComponent()

        EventObservable.bindSubscriber(this, eventObservable)

        lifecycle.addObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(this)
    }

    open fun onInitiateComponent() {

    }


}