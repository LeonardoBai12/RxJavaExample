package io.lb.rxjavaexample.model.todo

import com.google.gson.annotations.SerializedName

data class Todo(
    @SerializedName(TodoConstants.USER_ID)
    var userId: Int = 0,

    @SerializedName(TodoConstants.ID)
    var id: Int = 0,

    @SerializedName(TodoConstants.TITLE)
    var title: String? = null,

    @SerializedName(TodoConstants.IS_COMPLETED)
    var isCompleted: Boolean? = null,
)