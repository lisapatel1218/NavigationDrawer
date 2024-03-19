package com.example.navigationdrawer

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "notification_channel"
const val channelName = "com.eazyalgo.fcmpushnotification"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        println("onMessageReceived")
        if (remoteMessage.getNotification() != null) {
            generateNotification(
                remoteMessage.notification!!.title!!,
                remoteMessage.notification!!.body!!
            )
        }
    }

    fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteView = RemoteViews("com.example.navigationdrawer", R.layout.notification)


        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.message, message)
        remoteView.setImageViewResource(R.id.app_logo, R.drawable.firebase)

        return remoteView

    }

    fun generateNotification(title: String, message: String) {
        val intent = Intent(this, FCMPushNotification::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Your Channel Description"
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId).apply {
            setSmallIcon(R.drawable.firebase)
            setContentTitle(title)
            setContentText(message) // Consider removing if you're using a custom layout and this overwrites your custom content
            setAutoCancel(true)
            setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            setOnlyAlertOnce(true)
            setContentIntent(pendingIntent)
            // Uncomment the following line if you want to use BigTextStyle for better text visibility
            // setStyle(NotificationCompat.BigTextStyle().bigText(message))
        }

        // Uncomment if using custom RemoteViews
        // val remoteView = getRemoteView(title, message)
        // notificationBuilder.setContent(remoteView)

        notificationManager.notify(0, notificationBuilder.build())
    }
}