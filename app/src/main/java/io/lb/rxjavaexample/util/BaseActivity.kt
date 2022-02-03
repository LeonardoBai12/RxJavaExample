package io.lb.rxjavaexample.util

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import timber.log.Timber

open class BaseActivity: AppCompatActivity() {
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
}