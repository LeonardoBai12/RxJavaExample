package io.lb.rxjavaexample.ui.post

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.lb.rxjavaexample.databinding.ActivityPostBinding
import io.lb.rxjavaexample.model.post.Post
import io.lb.rxjavaexample.network.ServiceGenerator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class PostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding
    private val disposable = CompositeDisposable()
    private val postAdapter = PostAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupRecyclerView()

        setupPostObservable().subscribeOn(Schedulers.io())
            .flatMap {
                return@flatMap getCommentsObservable(it)
            }.observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Post> {
                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onNext(post: Post) {
                    updatePost(post)
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                }

                override fun onComplete() {
                }
            })
    }

    private fun getCommentsObservable(post: Post): Observable<Post> {
        return ServiceGenerator.requestApi.getComments(post.id).map {
            //Criado um delay pra simular carregamentos assíncronos em uma lista
            val delay = (Random().nextInt(5) + 1) * 1000
            Thread.sleep(delay.toLong())
            post.comments.addAll(it)
            return@map post
        }.subscribeOn(Schedulers.io())
    }

    private fun setupPostObservable(): Observable<Post> {
        return ServiceGenerator.requestApi
            .getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                updateList(it)
                return@flatMap Observable.fromIterable(it).subscribeOn(Schedulers.io())
            }
    }

    private fun setupRecyclerView() {
        val context = this

        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = postAdapter
        }
    }

    private fun updatePost(post: Post) {
        postAdapter.updatePost(post)
    }

    private fun updateList(posts: ArrayList<Post>) {
        postAdapter.updateList(posts)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}