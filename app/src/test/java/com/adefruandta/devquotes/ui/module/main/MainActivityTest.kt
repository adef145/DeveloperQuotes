package com.adefruandta.devquotes.ui.module.main

import android.content.Intent
import android.content.Intent.EXTRA_TEXT
import android.view.View
import com.adefruandta.devquotes.BaseTest
import com.adefruandta.devquotes.model.Quote
import com.adefruandta.devquotes.thirdparties.firebase.tracker.FirebaseTracker
import com.adefruandta.devquotes.thirdparties.firebase.tracker.FirebaseTrackerEvent
import com.adefruandta.devquotes.ui.component.quote.QuoteComponent
import com.adefruandta.devquotes.ui.event.IntentRefreshQuote
import com.adefruandta.devquotes.ui.event.IntentShareQuote
import com.adefruandta.devquotes.ui.event.Loaded
import com.happyfresh.happyarch.ComponentProvider
import com.happyfresh.happyarch.EventObservable
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Test

class MainActivityTest : BaseTest() {

    @MockK
    lateinit var eventObservable: EventObservable

    @MockK
    lateinit var componentProvider: ComponentProvider

    @MockK
    lateinit var quoteComponentView: View

    @MockK
    lateinit var mainViewModel: MainViewModel

    @MockK
    lateinit var firebaseTrackerEvent: FirebaseTrackerEvent

    private lateinit var activity: MainActivity

    override fun before() {
        super.before()
        activity = spyk(MainActivity())

        every { activity.eventObservable } returns eventObservable
        every { activity.componentProvider } returns componentProvider
        every { activity.quoteComponentView } returns quoteComponentView
        every { activity.mainViewModel } returns mainViewModel

        FirebaseTracker.init(firebaseTrackerEvent)
    }

    @Test
    fun onInitiateComponent_test() {
        activity.onInitiateComponent()

        verify { componentProvider.add(QuoteComponent::class.java, quoteComponentView) }
    }

    @Test
    fun onDidCreate_test() {
        activity.onDidCreate()

        mainViewModel.getQuote(eventObservable)
    }

    @Test
    fun onDidResume_test() {
        activity.quote = "Quote"

        activity.onDidResume()

        verify {
            firebaseTrackerEvent.viewMainScreen("Quote")
        }
    }

    @Test
    fun onQuoteLoaded_test() {
        activity.onQuoteLoaded(Loaded(Quote().apply {
            sr = "Quote"
            author = "Author"
        }))

        assert("Quote" == activity.quote)
    }

    @Test
    fun intentShareQuote() {
        val intentShareQuote = IntentShareQuote("\"Quote\" by Author")
        val intent = mockk<Intent>(relaxUnitFun = true, relaxed = true)

        mockkConstructor(Intent::class)
        mockkStatic(Intent::class)
        every { anyConstructed<Intent>().setAction(any()) } returns intent
        every {
            anyConstructed<Intent>().putExtra(
                EXTRA_TEXT,
                intentShareQuote.quote
            )
        } returns intent
        every {
            anyConstructed<Intent>().setType(any())
        } returns intent
        every { Intent.createChooser(any(), null) } returns intent
        every { activity.startActivity(any()) } returns Unit

        activity.intentShareQuote(intentShareQuote)

        verify {
            activity.startActivity(intent)
            firebaseTrackerEvent.shareQuote("\"Quote\" by Author")
        }
    }

    @Test
    fun intentRefreshQuote() {
        activity.intentRefreshQuote(IntentRefreshQuote())

        verify {
            mainViewModel.getQuote(eventObservable)
            firebaseTrackerEvent.refreshQuote()
        }
    }
}