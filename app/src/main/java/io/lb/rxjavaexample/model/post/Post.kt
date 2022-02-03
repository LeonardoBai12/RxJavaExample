package io.lb.rxjavaexample.model.post

import com.google.gson.annotations.SerializedName
import io.lb.rxjavaexample.model.comment.Comment
import io.lb.rxjavaexample.model.toDo.ToDo

data class Post(
    @SerializedName(PostConstants.USER_ID)
    var userId: Int = 0,

    @SerializedName(PostConstants.ID)
    var id: Int = 0,

    @SerializedName(PostConstants.TITLE)
    var title: String? = null,

    @SerializedName(PostConstants.BODY)
    var body: String? = null,

    @SerializedName(PostConstants.COMMENTS)
    var comments: ArrayList<Comment> = arrayListOf(),
)