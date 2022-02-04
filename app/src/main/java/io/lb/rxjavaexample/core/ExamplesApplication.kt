package io.lb.rxjavaexample.core

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.lb.rxjavaexample.BuildConfig
import io.lb.rxjavaexample.di.DaggerAppComponent
import timber.log.Timber

class ExamplesApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        return DaggerAppComponent.builder().application(this).build()
    }
}