package com.adefruandta.devquotes

import io.mockk.MockKAnnotations
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass

open class BaseTest {

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
            RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
        }

        @JvmStatic
        @AfterClass
        fun afterClass() {
            RxJavaPlugins.reset()
            RxAndroidPlugins.reset()
        }
    }

    @Before
    open fun before() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
    }
}