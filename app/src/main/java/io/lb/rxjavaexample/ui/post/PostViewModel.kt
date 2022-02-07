package io.lb.rxjavaexample.ui.post

import io.lb.rxjavaexample.model.post.Post
import io.lb.rxjavaexample.network.post.PostRepository
import io.lb.rxjavaexample.util.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class PostViewModel @Inject constructor(
    private val repository: PostRepository
) : BaseViewModel() {
    fun setupPostObservable(): Observable<ArrayList<Post>> {
        return repository.makeReactiveQueryForPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun setupCommentsObservable(post: Post): Observable<Post> {
        return repository.makeReactiveQueryForComments(post.id)
            .map {
                //Criado um delay pra simular carregamentos assíncronos em uma lista
                val delay = (Random().nextInt(2) + 1) * 1000
                Thread.sleep(delay.toLong())
                post.comments.addAll(it)
                return@map post
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}