package com.vadymhalaziuk.istesttask.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.vadymhalaziuk.istesttask.R
import com.vadymhalaziuk.istesttask.ui.MainActivity

private const val NOTIFICATION_CODE = 4

@SuppressLint("InlinedApi")
@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
fun Context.sendNotification(title: String, subtitle: String? = null) {

    val intent = Intent(this, MainActivity::class.java).apply {
        action = Intent.ACTION_VIEW
    }

    val pendingIntent = PendingIntent.getActivity(
        this,
        NOTIFICATION_CODE,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val notificationManager = NotificationManagerCompat.from(this)

    val channelId = this.getString(R.string.notificationChannelId)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelName = this.getString(R.string.notificationChannelId)
        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
    }
    val notificationBuilder = NotificationCompat.Builder(this, channelId).apply {
        setSmallIcon(R.drawable.ic_launcher_background)
        setContentTitle(title)
        setContentText(subtitle)
        setAutoCancel(true)
        setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        setContentIntent(pendingIntent)
    }

    notificationBuilder.color = ContextCompat.getColor(this, R.color.purple_500)

    val notificationId = (Math.random() * 10000).toInt()

    notificationManager.notify(notificationId, notificationBuilder.build())
}