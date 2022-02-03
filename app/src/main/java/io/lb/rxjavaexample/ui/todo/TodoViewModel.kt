package io.lb.rxjavaexample.ui.todo

import androidx.lifecycle.LiveData
import io.lb.rxjavaexample.model.todo.Todo
import io.lb.rxjavaexample.network.todo.TodoRepository
import io.lb.rxjavaexample.util.BaseViewModel

class TodoViewModel : BaseViewModel() {
    private val repository = TodoRepository()

    fun makeQuery(): LiveData<ArrayList<Todo>> {
        return repository.makeReactiveQueryForTodos().toLiveData()
    }
}