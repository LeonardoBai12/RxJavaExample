package io.lb.rxjavaexample.model.post

import com.google.gson.annotations.SerializedName
import io.lb.rxjavaexample.model.comment.Comment

data class Post(
    @SerializedName("userId")
    var userId: Int = 0,

    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("body")
    var body: String? = null,

    var comments: ArrayList<Comment> = arrayListOf(),
)