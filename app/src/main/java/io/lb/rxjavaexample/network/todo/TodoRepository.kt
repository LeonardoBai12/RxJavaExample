package io.lb.rxjavaexample.network.todo

import io.lb.rxjavaexample.model.todo.Todo
import io.lb.rxjavaexample.network.RetrofitServiceInterface
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class TodoRepository(
    private val retrofitServiceInterface: RetrofitServiceInterface,
) {
    fun makeReactiveQueryForTodos(): Observable<ArrayList<Todo>> {
        return retrofitServiceInterface.getToDos().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}