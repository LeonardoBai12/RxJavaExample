package io.lb.rxjavaexample.di.post

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.lb.rxjavaexample.di.ViewModelKey
import io.lb.rxjavaexample.ui.post.PostViewModel

@Module
abstract class PostViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(PostViewModel::class)
    abstract fun bindPostViewModel(viewModel: PostViewModel) : ViewModel
}