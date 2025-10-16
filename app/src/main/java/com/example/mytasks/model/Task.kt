package com.example.mytasks.model

data class Task(
    val id: Int,
    val name: String,
    val isCompleted: Boolean = false
)
