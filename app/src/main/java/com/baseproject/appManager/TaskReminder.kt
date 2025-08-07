package com.baseproject.appManager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.baseproject.MainActivity
import com.baseproject.data.models.Task
import com.baseproject.navigation.Route
import java.util.concurrent.TimeUnit

class TaskReminder(
   val context: Context? = null
) {

    fun scheduleReminder(task: Task) {
        val workManager = WorkManager.getInstance(context?: return)

        /**
         * TODO
         * Update AddTaskUI to accommodate reminder time
         */
        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(3, TimeUnit.SECONDS)
            .addTag(task.id.toString())
            .setInputData(workDataOf(
                "title" to task.title,
                "description" to task.description,
                "taskId" to task.id.toString()
            )).build()
        workManager.enqueue(workRequest)
    }

}

private fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Task Reminders"
        val descriptionText = "Channel for task reminder notifications"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("TODO_APP", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

private fun sendNotification(
    context: Context,
    title: String,
    description: String,
    taskIdString: String?
){
    val notificationId = taskIdString?.hashCode() ?: System.currentTimeMillis().toInt()

    createNotificationChannel(context)

    val builder = NotificationCompat.Builder(context, "TODO_APP")
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle(title)
        .setContentText(description)
        .setContentIntent(createPendingIntent(context, taskIdString, notificationId))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    with(NotificationManagerCompat.from(context)) {
        notify(notificationId, builder.build())
    }

}


private fun createPendingIntent(
    context: Context,
    taskIdString: String?,
    notificationId: Int): PendingIntent {
    val intent = Intent(
        Intent.ACTION_VIEW,
        "com.baseproject://${Route.HOME}/$taskIdString".toUri(),
        context,
        MainActivity::class.java
    )
    return PendingIntent.getActivity(
        context,
        notificationId,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
}


class ReminderWorker(
    appContext: Context, // Renamed for clarity, it's the application context
    params: WorkerParameters
): Worker(appContext, params){

    override fun doWork(): Result {
        val title = inputData.getString("title") ?: "Task Reminder"
        val description = inputData.getString("description") ?: "You have a pending task."
        val taskIdString = inputData.getString("taskId")

        sendNotification(applicationContext, title, description, taskIdString)
        return Result.success()
    }
}