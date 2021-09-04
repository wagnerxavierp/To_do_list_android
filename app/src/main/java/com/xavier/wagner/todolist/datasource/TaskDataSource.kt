package com.xavier.wagner.todolist.datasource

import com.xavier.wagner.todolist.model.Task

object TaskDataSource {
    const val TASK_ID = "TASK_ID"

    private val list = arrayListOf<Task>()

    fun getList() = list.toList()

    fun insertTask(task: Task){
        if (task.id == 0){
            list.add(task.copy(id = list.size + 1))
        }else{
            findById(task.id)?.let {
                removeTask(it)
            }
            list.add(task)
        }

    }

    fun removeTask(task: Task){
        list.remove(task)
    }

    fun findById(taskId: Int) = list.find { it.id == taskId }

}