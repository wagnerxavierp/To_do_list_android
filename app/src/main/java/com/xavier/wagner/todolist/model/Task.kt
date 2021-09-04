package com.xavier.wagner.todolist.model

import java.io.Serializable

data class Task(
    val id: Int = 0,
    val title: String,
    val hour: String,
    val date: String
) : Serializable
