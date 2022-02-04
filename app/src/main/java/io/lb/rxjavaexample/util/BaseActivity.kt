package io.lb.rxjavaexample.util

import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber

open class BaseActivity: DaggerAppCompatActivity() {
    val disposable = CompositeDisposable()

    fun <T> Observable<T>.defaultSubscribe(onNext: (T) -> Unit) {
        subscribe(object : Observer<T> {
            override fun onSubscribe(d: Disposable) {
                Timber.d("onSubscribe called")
                disposable.add(d)
            }

            override fun onNext(task: T) {
                onNext(task)
            }

            override fun onError(e: Throwable) {
                Timber.e("onError called")
            }

            override fun onComplete() {
                Timber.d( "onComplete called")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()

        // Dispose é como se fosse um hard clear, enquanto o clear só limpa os subcribers
        // e tudo mais, sem "matar" os observables -> geralmente é melhor usar o clear()
        // disposable.dispose()
    }
}