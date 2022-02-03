package io.lb.rxjavaexample.ui.todo

import android.arch.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.lb.rxjavaexample.model.todo.Todo
import io.lb.rxjavaexample.network.todo.TodoRepository

class TodoViewModel : ViewModel() {
    private val repository = TodoRepository()

    fun makeQuery(): LiveData<ArrayList<Todo>> {
        return repository.makeReactiveQueryForTodos()
    }
}