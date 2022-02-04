package io.lb.rxjavaexample.ui.todo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.lb.rxjavaexample.databinding.ActivityTodoBinding
import io.lb.rxjavaexample.model.todo.Todo
import io.lb.rxjavaexample.util.BaseActivity
import timber.log.Timber
import javax.inject.Inject

class TodoActivity : BaseActivity() {
    private lateinit var binding: ActivityTodoBinding
    private val todoAdapter = TodoAdapter()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val todoViewModel: TodoViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTodoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupRecyclerView()
        setupViewModel()

    }

    private fun setupViewModel() {
        todoViewModel.makeQuery().observe(this) {
            updateList(it ?: arrayListOf())
        }
    }

    private fun updateList(todos: ArrayList<Todo>) {
        todoAdapter.updateList(todos)
    }

    private fun setupRecyclerView() {
        val context = this

        binding.rvTodos.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
        }
    }

}