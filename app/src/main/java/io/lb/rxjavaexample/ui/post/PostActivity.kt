package io.lb.rxjavaexample.ui.post

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.lb.rxjavaexample.databinding.ActivityPostBinding
import io.lb.rxjavaexample.model.post.Post
import io.lb.rxjavaexample.network.RetrofitServiceInterface
import io.lb.rxjavaexample.util.BaseActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class PostActivity : BaseActivity() {
    private lateinit var binding: ActivityPostBinding
    private val postAdapter = PostAdapter()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val postViewModel: PostViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupRecyclerView()

        setupPostObservable().subscribeOn(Schedulers.io())
            .flatMap {
                return@flatMap postViewModel.getCommentsObservable(it)
            }.defaultSubscribe {
                updatePost(it)
            }
    }

    private fun setupPostObservable(): Observable<Post> {
        return postViewModel.setupPostObservable()
            .flatMap {
                // Flat map vai carregar em ordens aleatórias, se eu mudar pra concatMap, vai carregar em ordem
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
}