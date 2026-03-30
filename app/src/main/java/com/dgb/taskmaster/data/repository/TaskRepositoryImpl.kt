package com.dgb.taskmaster.data.repository

import com.dgb.taskmaster.data.local.dao.TaskDao
import com.dgb.taskmaster.data.mapper.toDomain
import com.dgb.taskmaster.data.mapper.toEntity
import com.dgb.taskmaster.domain.model.Task
import com.dgb.taskmaster.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val taskDao: TaskDao
): TaskRepository {
    override fun getAllTasks(): Flow<List<Task>> {
        return  taskDao.getAllTask().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun insertTask(task: Task) {
        return  taskDao.insertTask(task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
       return taskDao.updateTask(task.toEntity())
    }

    override suspend fun deleteTask(task: Task) {
        return taskDao.deleteTask(task.toEntity())
    }
}