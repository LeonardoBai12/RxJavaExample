package io.lb.rxjavaexample.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.lb.rxjavaexample.databinding.ActivityMainBinding
import io.lb.rxjavaexample.model.task.Task
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

        // Primeiro exemplo utilizando fromIterable com uma lista pré-definida
        //setupTaskObservableFromIterable()

        // Exemplo de utilização de Create - cria um emitter pra criar dinamicamente
        // (é o mais flexível)
        //setupTaskObservableCreate()

        // Exemplo de utilização de Just - apenas um objeto
        //setupTaskObservableJust()

        // Exemplo de utilização de Range - apenas um intervalo numérico
        //setupTaskObservableRange()

        // Exemplo de utilização de Range + Repeat - repete uma mesma operação várias vezes
        //setupTaskObservableRepeat()

        // Exemplo de Interval com TakeWhile - intervale de tempo com o TakeWhile limitando
        //setupTaskObservableInterval()

        // Exemplo de Timer - efetua a operação de um observable após o tempo determinado ter se passado
        //setupTaskObservableTimer()

        // Acesso à tela de posts, onde foi criado um exemplo de trabalho com flatMap
        setupOnClickPostButton()
    }

    private fun setupTaskObservableTimer() {
        val taskObservable = Observable.timer(3, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        taskObservable.defaultSubscribe {
            Timber.d("onNext called: ${Thread.currentThread().name}")
            Timber.d("onNext called: $it seconds")
        }
    }

    private fun setupTaskObservableInterval() {
        val taskObservable = Observable.interval(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .takeWhile {
                return@takeWhile it < 10
            }.observeOn(AndroidSchedulers.mainThread())

        taskObservable.defaultSubscribe {
            Timber.d("onNext called: ${Thread.currentThread().name}")
            Timber.d("onNext called: $it seconds")
        }
    }

    private fun setupTaskObservableRepeat() {
        val taskObservable = Observable.range(0, 3)
            .subscribeOn(Schedulers.io())
            .map {
                // Tudo aqui dentro será executado em uma background thread
                return@map Task("Priority: $it", false, it)
            }.repeat(3)
            .observeOn(AndroidSchedulers.mainThread())

        taskObservable.defaultSubscribe {
            Timber.d("onNext called: ${Thread.currentThread().name}")
            Timber.d("onNext called: ${it.description}")
        }
    }

    private fun setupTaskObservableRange() {
        val taskObservable = Observable.range(0, 9)
            .subscribeOn(Schedulers.io())
            .map {
                // Tudo aqui dentro será executado em uma background thread
                return@map Task("Priority: $it", false, it)
            }.takeWhile {
                //Apenas exemplo pra mostrar como funciona takeWhile, ele está em prioridade em relação ao range
                return@takeWhile it.priority < 5
            }.observeOn(AndroidSchedulers.mainThread())

        taskObservable.defaultSubscribe {
            Timber.d("onNext called: ${Thread.currentThread().name}")
            Timber.d("onNext called: ${it.description}")
        }
    }

    private fun setupTaskObservableCreate() {
        val task = Task("Example task do create shit", false, 1)
        val taskObservable = Observable.create<Task> {
            // Posso pegar a lista pré-definida pra fazer um loop fazendo isso,
            //fica basicamente a mesma que com Iterable
            if (!it.isDisposed) {
                it.onNext(task)
                it.onComplete()
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        taskObservable.defaultSubscribe {
            Timber.d("onNext called: ${Thread.currentThread().name}")
            Timber.d("onNext called: ${it.description}")
        }
    }

    private fun setupTaskObservableJust() {
        val task = Task("Example task do create shit", false, 1)
        val taskObservable = Observable.just(task)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        taskObservable.defaultSubscribe {
            Timber.d("onNext called: ${Thread.currentThread().name}")
            Timber.d("onNext called: ${it.description}")
        }
    }

    private fun setupOnClickPostButton() {
        binding.btnPostsActivity.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupTaskObservableFromIterable() {
        val taskObservable = Observable.fromIterable(DataSource.createTaskList())
            .subscribeOn(Schedulers.io())
            .filter { task ->
                Timber.d("test: ${Thread.currentThread().name}")
                task.isComplete
            }
            .observeOn(AndroidSchedulers.mainThread())

        taskObservable.defaultSubscribe {
            Timber.d("onNext called: ${Thread.currentThread().name}")
            Timber.d("onNext called: ${it.description}")
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
