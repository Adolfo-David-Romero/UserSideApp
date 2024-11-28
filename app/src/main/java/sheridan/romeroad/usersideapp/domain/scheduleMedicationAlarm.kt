package sheridan.romeroad.usersideapp.domain

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import sheridan.romeroad.usersideapp.data.MedicationReminder
import sheridan.romeroad.usersideapp.services.MedicationAlarmReceiver
import java.text.SimpleDateFormat
import java.util.*

/**
 * Student ID: 991555778
 * UserSideApp
 * created by davidromero
 * on 2024-11-28
 **/

@SuppressLint("MissingPermission", "ScheduleExactAlarm")
fun scheduleMedicationAlarm(context: Context, reminder: MedicationReminder) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val intent = Intent(context, MedicationAlarmReceiver::class.java).apply {
        putExtra("name", reminder.name)
        putExtra("time", reminder.time)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        reminder.id.hashCode(), // Unique ID for each reminder
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    val reminderTime = Calendar.getInstance().apply {
        time = timeFormat.parse(reminder.time) ?: Date()
    }

    alarmManager.setExact(
        AlarmManager.RTC_WAKEUP,
        reminderTime.timeInMillis,
        pendingIntent
    )
}