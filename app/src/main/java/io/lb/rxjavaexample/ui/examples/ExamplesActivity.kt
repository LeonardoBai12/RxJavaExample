package io.lb.rxjavaexample.ui.examples

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import io.lb.rxjavaexample.databinding.ActivityExamplesBinding
import io.lb.rxjavaexample.model.task.Task
import io.lb.rxjavaexample.util.BaseActivity
import io.lb.rxjavaexample.util.DataSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class ExamplesActivity : BaseActivity() {
    private lateinit var binding: ActivityExamplesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())

        binding = ActivityExamplesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Exemplo de Debounce - faz o subscribe após um delay para limitar a quantidade de requisições
        // Pesquisa do instagram funciona assim, se fosse fazer letra a letra teriam requisições em muito maior escala
        // no caso de .debounce(1, TimeUnit.SECONDS), assim que eu parar de digitar, um segundo depois, ele faz a requisição
        setupTaskObservableDebounce()

        // Exemplo de ThrottleFirst - resolve o problema de "button spamming"
        // Só permite fazer uma segunda requisição após um intervalo de tempo
        setupTaskObservableThrottleFirst()

        binding.btnRunExample.setOnClickListener {
            // Primeiro exemplo utilizando fromIterable com uma lista pré-definida
            //setupTaskObservableFromIterableFilterDistinctTake()

            // Exemplo de utilização de Create - cria um emitter pra criar dinamicamente
            // (é o mais flexível)
            //setupTaskObservableCreate()

            // Exemplo de utilização de Just - apenas um objeto
            //setupTaskObservableJust()

            // Exemplo de utilização de Range - apenas um intervalo numérico
            //setupTaskObservableRange()

            // Exemplo de utilização de Range + Repeat - repete uma mesma operação várias vezes
            //setupTaskObservableRepeatMap()

            // Exemplo de Interval com TakeWhile - intervale de tempo com o TakeWhile limitando
            //setupTaskObservableInterval()

            // Exemplo de Timer - efetua a operação de um observable após o tempo determinado ter se passado
            //setupTaskObservableTimer()

            // Exemplo de buffer - é um transformador, assim como map, debounce e flatmap
            // Buffer vai fazer com que seja possível admitir objetos em grupos.
            // Por exemplo, assim que tivermos 5 objetos prontos para serem admitidos, vamos para os próximos 5
            // Também pode ser usado .buffer(4, Time.SECONDS), onde a cada 4 segundos ele verificará se houve alguma atualização
            //setupTaskObservableBuffer()
        }

    }

    private fun setupTaskObservableThrottleFirst() {
        val taskObservable = Observable.create<Task> { emitter ->
            val task = Task("throttleFirst", false, 1 )
            binding.btnThrottleFirstExample.setOnClickListener {
                if (!emitter.isDisposed) {
                    emitter.onNext(task)
                }
            }
        }.throttleFirst(4000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        taskObservable.defaultSubscribe {
            Timber.d("onNext called: ${Thread.currentThread().name}")
            Timber.d("onNext called: $it")
            Toast.makeText(this, it.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupTaskObservableDebounce() {
        val observableQueryText = Observable.create<String> { emitter ->
            binding.svSearchExample.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(text: String?): Boolean {
                    if (!emitter.isDisposed) {
                        emitter.onNext(text ?: "")
                    }
                    return false
                }

                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }
            })
        }.debounce(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())

        observableQueryText.defaultSubscribe {
            Timber.d("onNext called: ${Thread.currentThread().name}")
            Timber.d("onNext called: $it")
        }
    }

    private fun setupTaskObservableBuffer() {
        val taskObservable = Observable.fromIterable(DataSource.createTaskList())
            .buffer(2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        taskObservable.defaultSubscribe {
            Timber.d("onNext called: ${Thread.currentThread().name}")
            Timber.d("onNext called: $it")
        }
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

    private fun setupTaskObservableRepeatMap() {
        val taskObservable = Observable.range(0, 3)
            .subscribeOn(Schedulers.io())
            .map {
                // Tudo aqui dentro será executado em uma background thread
                // Map transforma um tipo de objeto em outro para ser observado.
                // No caso transforma inteiros do repeat em tarefas.
                // Poderia transformar uma task em strings, por exemplo
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

    private fun setupTaskObservableFromIterableFilterDistinctTake() {
        // fromCallable(útil pra trabalhar com Room ou Retrofit), fromArray e fromIterable - auto explicativo
        val taskObservable = Observable.fromIterable(DataSource.createTaskList())
            .subscribeOn(Schedulers.io())
            .filter { task ->
                // Neste caso, só vai observar as tarefas completas
                // Filter, como o nome diz, é como se fosse um WHERE fora da query
                Timber.d("test: ${Thread.currentThread().name}")
                return@filter task.isComplete
            }.distinct {
                // Neste caso, se tiver tarefas com a mesma descrição, não irá repeti-las
                return@distinct it.description
            }.take(3) // Take fará mostrar apenas a quantidade de elementos parametrizada
            .observeOn(AndroidSchedulers.mainThread())

        taskObservable.defaultSubscribe {
            Timber.d("onNext called: ${Thread.currentThread().name}")
            Timber.d("onNext called: ${it.description}")
        }
    }
}
