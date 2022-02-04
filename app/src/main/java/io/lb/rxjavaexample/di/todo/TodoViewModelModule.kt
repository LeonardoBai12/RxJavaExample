package io.lb.rxjavaexample.di.todo

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.lb.rxjavaexample.di.ViewModelKey
import io.lb.rxjavaexample.ui.todo.TodoViewModel

@Module
abstract class TodoViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TodoViewModel::class)
    abstract fun bindTodoViewModel(viewModel: TodoViewModel) : ViewModel
}