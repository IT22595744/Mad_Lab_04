package com.example.to_do_1.Model


class ToDoModel {
    private var id: Int = 0
    private var status: Int = 0
    private var task: String = ""

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getStatus(): Int {
        return status
    }

    fun setStatus(status: Int) {
        this.status = status
    }

    fun getTask(): String {
        return task
    }

    fun setTask(task: String) {
        this.task=task
        }
}