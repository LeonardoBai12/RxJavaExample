package io.lb.rxjavaexample.network

import io.lb.rxjavaexample.model.comment.Comment
import io.lb.rxjavaexample.model.post.Post
import io.lb.rxjavaexample.model.todo.Todo
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface RequestApi {
    @GET("posts")
    fun getPosts(): Observable<ArrayList<Post>>

    @GET("posts/{id}/comments")
    fun getComments(@Path("id") id: Int): Observable<List<Comment>>

    @GET("todos")
    fun getToDos(): Flowable<ArrayList<Todo>>
}