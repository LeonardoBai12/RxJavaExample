package io.lb.rxjavaexample.ui.post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.lb.rxjavaexample.R
import io.lb.rxjavaexample.model.post.Post
import kotlin.Boolean
import kotlin.Int


class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    private var posts = arrayListOf<Post>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun updateList(posts: ArrayList<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    fun updatePost(post: Post) {
        posts[posts.indexOf(post)] = post
        notifyItemChanged(posts.indexOf(post))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvPostTitle: TextView = view.findViewById(R.id.tvPostTitle)
        private val tvComments: TextView = view.findViewById(R.id.tvComments)
        private val pbPostProgress: ProgressBar = view.findViewById(R.id.pbPostProgress)

        fun bind(post: Post) {
            tvPostTitle.text = post.title
            if (post.comments.isEmpty()) {
                showProgressBar(true)
                tvComments.text = ""
            } else {
                showProgressBar(false)
                tvComments.text = post.comments.size.toString()
            }
        }

        private fun showProgressBar(showProgressBar: Boolean) {
            if (showProgressBar) {
                pbPostProgress.visibility = View.VISIBLE
            } else {
                pbPostProgress.visibility = View.GONE
            }
        }
    }
}