package io.lb.rxjavaexample.ui.todo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.lb.rxjavaexample.databinding.ActivityTodoBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber

class TodoActivity: AppCompatActivity() {
    private lateinit var binding: ActivityTodoBinding
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())

        binding = ActivityTodoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
}