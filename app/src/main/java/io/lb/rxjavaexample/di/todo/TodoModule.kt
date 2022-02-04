package io.lb.rxjavaexample.di.todo

import dagger.Module
import dagger.Provides
import io.lb.rxjavaexample.network.RetrofitServiceInterface
import io.lb.rxjavaexample.network.todo.TodoRepository

@Module
class TodoModule {
    @Provides
    fun providesTodosRepository(
        retrofitServiceInterface: RetrofitServiceInterface,
    ): TodoRepository {
        return TodoRepository(retrofitServiceInterface)
    }
}