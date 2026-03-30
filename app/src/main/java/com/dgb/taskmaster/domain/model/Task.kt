package com.dgb.taskmaster.domain.model

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean,
    val createdAt: Long,
)
