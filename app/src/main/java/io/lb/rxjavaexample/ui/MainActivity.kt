package io.lb.rxjavaexample.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.lb.rxjavaexample.databinding.ActivityMainBinding
import io.lb.rxjavaexample.model.task.Task
import io.lb.rxjavaexample.util.DataSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val taskObservable = Observable.fromIterable(DataSource.createTaskList())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        taskObservable.subscribe(object : Observer<Task> {
            override fun onSubscribe(d: Disposable) {
                Log.d("MainActivity", "onSubscribe called")
            }
            override fun onNext(task: Task) {
                Log.d("MainActivity", "onNext called: ${Thread.currentThread().name}")
                Log.d("MainActivity", "onNext called: ${task.description}" )
            }
            override fun onError(e: Throwable) {
                Log.e("MainActivity", "onError called")
            }
            override fun onComplete() {
                Log.d("MainActivity", "onComplete called")
            }
        })
    }
}