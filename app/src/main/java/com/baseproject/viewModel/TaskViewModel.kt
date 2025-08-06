package com.baseproject.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.baseproject.data.models.Task
import com.baseproject.db.TaskDatabase

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    val taskDao = TaskDatabase.getInstance(application).taskDao()

    fun getAllTasks() = taskDao.getAllTasks()
    suspend fun insertTask(task: Task) = taskDao.insertTask(task)
    suspend fun updateTask(task: Task) = taskDao.updateTask(
        id = task.id,
        updatedAt = task.updatedAt,
        isCompleted = task.isCompleted
    )
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
    suspend fun deleteAllTasks() = taskDao.deleteAllTasks()


}