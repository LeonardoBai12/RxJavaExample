package io.lb.rxjavaexample.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.lb.rxjavaexample.databinding.ActivityMainBinding
import io.lb.rxjavaexample.model.task.Task
import io.lb.rxjavaexample.ui.examples.ExamplesActivity
import io.lb.rxjavaexample.ui.post.PostActivity
import io.lb.rxjavaexample.util.DataSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Acesso à tela de posts, onde foi criado um exemplo de trabalho com flatMap
        setupOnClickPostButton()

        // Acesso à tela de exemplos, onde foram criados vários exemplos de funcionalidades do RxJava
        setupOnClickExamplesButton()
    }

    private fun setupOnClickExamplesButton() {
        binding.btnExamplesActivity.setOnClickListener {
            val intent = Intent(this, ExamplesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupOnClickPostButton() {
        binding.btnPostsActivity.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }
    }

    private fun <T> Observable<T>.defaultSubscribe(onNext: (T) -> Unit) {
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
