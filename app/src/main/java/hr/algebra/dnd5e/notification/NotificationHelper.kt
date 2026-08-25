package hr.algebra.dnd5e.notification

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import hr.algebra.dnd5e.HostActivity
import hr.algebra.dnd5e.R
import kotlin.jvm.java

object NotificationHelper {

    private const val CHANNEL_ID = "dnd5e_reminders"
    private const val CHANEL_NAME = "D&D reminders"
    private const val NOTIFICATION_ID = 1001
    private const val REQ_ALARM=3001

    fun ensureChannel(context: Context) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channel= NotificationChannel(
            CHANNEL_ID,
            CHANEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply{
            description = "Notifications from 5e Character Handbook"
        }
        val nm = context.getSystemService(NotificationManager::class.java)
        nm.createNotificationChannel(channel)
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showReminder(context: Context){
        val openApp = Intent(context, HostActivity::class.java).apply{
            flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingOpen = PendingIntent.getActivity(
            context,
            2001,
            openApp,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText("Your adventure awaits \\u2014 tap to open.")
            .setContentIntent(pendingOpen)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }
    fun scheduleIn30seconds(context: Context){
        val alarmManager=context.getSystemService(AlarmManager::class.java)

        val intent=Intent(context, AlarmReceiver::class.java)
        val pendingIntent=PendingIntent.getBroadcast(
            context,
            REQ_ALARM,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val triggerAt=System.currentTimeMillis()+30_000L
        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAt, pendingIntent)
    }

}