package io.lb.rxjavaexample.ui.post

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.lb.rxjavaexample.R
import io.lb.rxjavaexample.model.post.Post

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    private var posts = ArrayList<Post>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val picture = posts[position]
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun updateList(posts: ArrayList<Post>) {
        this.posts = posts
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvPostTitle: TextView = view.findViewById(R.id.tvPostTitle)
        val tvComments: TextView = view.findViewById(R.id.tvComments)
        val pbPostProgress: ProgressBar = view.findViewById(R.id.pbPostProgress)
    }
}