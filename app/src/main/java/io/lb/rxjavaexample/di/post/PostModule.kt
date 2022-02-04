package io.lb.rxjavaexample.di.post

import dagger.Module
import dagger.Provides
import io.lb.rxjavaexample.network.RetrofitServiceInterface
import io.lb.rxjavaexample.network.post.PostRepository
import io.lb.rxjavaexample.network.todo.TodoRepository

@Module
class PostModule {
    @Provides
    fun providesPostRepository(
        retrofitServiceInterface: RetrofitServiceInterface,
    ): PostRepository {
        return PostRepository(retrofitServiceInterface)
    }
}