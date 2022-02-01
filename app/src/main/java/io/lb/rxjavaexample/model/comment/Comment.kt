package io.lb.rxjavaexample.model.comment

import com.google.gson.annotations.SerializedName

data class Comment (
    @SerializedName(CommentConstants.POST_ID)
    var postId: Int = 0,

    @SerializedName(CommentConstants.ID)
    var id: Int = 0,

    @SerializedName(CommentConstants.NAME)
    var name: String? = null,

    @SerializedName(CommentConstants.EMAIL)
    var email: String? = null,

    @SerializedName(CommentConstants.BODY)
    var body: String? = null
)