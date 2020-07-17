package com.adefruandta.devquotes.ui.module

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import com.happyfresh.happyarch.ComponentProvider
import com.happyfresh.happyarch.ComponentProviders
import com.happyfresh.happyarch.EventObservable

abstract class BaseFragment : Fragment(), LifecycleObserver {

    abstract val layoutResId: Int

    val eventObservable: EventObservable by lazy {
        EventObservable.get(this)
    }

    val componentProvider: ComponentProvider by lazy {
        ComponentProviders.of(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitiateComponent()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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