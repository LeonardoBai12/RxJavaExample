package io.lb.rxjavaexample.model.post

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("userId")
    private val userId: Int = 0,

    @SerializedName("id")
    private val id: Int = 0,

    @SerializedName("title")
    private val title: String? = null,

    @SerializedName("body")
    private val body: String? = null,
)