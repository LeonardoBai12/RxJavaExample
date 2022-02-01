package io.lb.rxjavaexample.model.comment

import com.google.gson.annotations.SerializedName

data class Comment (
    @SerializedName(CommentConstants.POST_ID)
    private var postId: Int = 0,

    @SerializedName(CommentConstants.ID)
    private var id: Int = 0,

    @SerializedName(CommentConstants.NAME)
    private var name: String? = null,

    @SerializedName(CommentConstants.EMAIL)
    private var email: String? = null,

    @SerializedName(CommentConstants.BODY)
    private var body: String? = null
)