package io.lb.rxjavaexample.ui.post

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.lb.rxjavaexample.databinding.ActivityPostBinding
import io.lb.rxjavaexample.model.post.Post
import io.lb.rxjavaexample.network.ServiceGenerator
import io.lb.rxjavaexample.util.BaseActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class PostActivity : BaseActivity() {

    private lateinit var binding: ActivityPostBinding
    private val postAdapter = PostAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())

        binding = ActivityPostBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupRecyclerView()

        setupPostObservable().subscribeOn(Schedulers.io())
            .flatMap {
                return@flatMap getCommentsObservable(it)
            }.observeOn(AndroidSchedulers.mainThread())
            .defaultSubscribe {
                updatePost(it)
            }
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