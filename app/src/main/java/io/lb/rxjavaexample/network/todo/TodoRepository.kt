package io.lb.rxjavaexample.network.todo

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import io.lb.rxjavaexample.model.todo.Todo
import io.lb.rxjavaexample.network.ServiceGenerator.requestApi
import io.reactivex.rxjava3.schedulers.Schedulers


class TodoRepository {
    //FromPublisher vai transformar um Flowable para LiveData e toPublisher faz o contrário
    fun makeReactiveQueryForTodos(): LiveData<ArrayList<Todo>> {
        return LiveDataReactiveStreams.fromPublisher(
            requestApi.getToDos().subscribeOn(Schedulers.io())
        )
    }
}