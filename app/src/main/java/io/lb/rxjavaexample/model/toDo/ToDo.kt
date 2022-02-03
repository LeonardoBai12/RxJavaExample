package io.lb.rxjavaexample.model.toDo

import com.google.gson.annotations.SerializedName

data class ToDo(
    @SerializedName(ToDoConstants.USER_ID)
    var userId: Int = 0,

    @SerializedName(ToDoConstants.ID)
    var id: Int = 0,

    @SerializedName(ToDoConstants.TITLE)
    var title: String? = null,

    @SerializedName(ToDoConstants.IS_COMPLETED)
    var isCompleted: Boolean? = null,
)