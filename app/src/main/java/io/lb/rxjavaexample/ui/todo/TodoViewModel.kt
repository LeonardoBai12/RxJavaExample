package io.lb.rxjavaexample.ui.todo

import androidx.lifecycle.LiveData
import io.lb.rxjavaexample.model.todo.Todo
import io.lb.rxjavaexample.network.todo.TodoRepository
import io.lb.rxjavaexample.util.BaseViewModel
import javax.inject.Inject

class TodoViewModel @Inject constructor(
    private val repository: TodoRepository
) : BaseViewModel() {

    fun makeQuery(): LiveData<ArrayList<Todo>> {
        // Um exemplo caso quiséssemos transformar um observável em LiveData
        return repository.makeReactiveQueryForTodos().toLiveData()
    }
}