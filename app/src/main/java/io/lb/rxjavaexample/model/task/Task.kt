package io.lb.rxjavaexample.model.task

data class Task (
    val description: String,
    val isComplete: Boolean,
    val priority: Int,
)