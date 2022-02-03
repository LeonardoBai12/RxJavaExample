package io.lb.rxjavaexample.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable

open class BaseViewModel: ViewModel() {
    fun <T> Observable<T>.toLiveData() : LiveData<T> =
        MutableLiveData<T>().apply {
            this@toLiveData.subscribe { value = it }
        }
}