package io.lb.rxjavaexample.network.todo

import io.lb.rxjavaexample.model.todo.Todo
import io.lb.rxjavaexample.network.ServiceGenerator.requestApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class TodoRepository {
    fun makeReactiveQueryForTodos(): Observable<ArrayList<Todo>> {
        return requestApi.getToDos().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }
}