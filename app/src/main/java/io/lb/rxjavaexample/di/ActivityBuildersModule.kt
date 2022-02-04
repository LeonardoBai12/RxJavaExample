package io.lb.rxjavaexample.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.lb.rxjavaexample.di.post.PostModule
import io.lb.rxjavaexample.di.post.PostViewModelModule
import io.lb.rxjavaexample.di.todo.TodoModule
import io.lb.rxjavaexample.di.todo.TodoViewModelModule
import io.lb.rxjavaexample.ui.examples.ExamplesActivity
import io.lb.rxjavaexample.ui.main.MainActivity
import io.lb.rxjavaexample.ui.post.PostActivity
import io.lb.rxjavaexample.ui.todo.TodoActivity

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(
        modules = [
            PostModule::class,
            PostViewModelModule::class,
        ]
    )
    abstract fun contributePostActivity(): PostActivity

    @ContributesAndroidInjector
    abstract fun contributeExamplesActivity(): ExamplesActivity

    @ContributesAndroidInjector(
        modules = [
            TodoModule::class,
            TodoViewModelModule::class,
        ]
    )
    abstract fun contributeTodoActivity(): TodoActivity
}