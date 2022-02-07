package io.lb.rxjavaexample.network.post

import io.lb.rxjavaexample.model.comment.Comment
import io.lb.rxjavaexample.model.post.Post
import io.lb.rxjavaexample.network.RetrofitServiceInterface
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class PostRepository(
    private val retrofitServiceInterface: RetrofitServiceInterface,
) {
    fun makeReactiveQueryForPosts(): Observable<ArrayList<Post>> {
        return retrofitServiceInterface.getPosts().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun makeReactiveQueryForComments(id: Int): Observable<List<Comment>> {
        return retrofitServiceInterface.getComments(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}