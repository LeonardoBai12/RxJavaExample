package io.lb.rxjavaexample.util

import io.lb.rxjavaexample.model.task.Task

object DataSource {
    fun createTaskList(): ArrayList<Task> {
        return arrayListOf(
            Task("Task 1", true, 3),
            Task("Big Ass Important Task", false, 2),
            Task("Task 2", true, 0),
            Task("Very big and creative name to give to a very simples task", false, 1),
            Task("Yes", true, 4),
        )
    }
}