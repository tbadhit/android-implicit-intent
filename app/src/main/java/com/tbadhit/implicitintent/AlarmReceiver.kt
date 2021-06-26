package com.tbadhit.implicitintent

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val mediaPlayer = MediaPlayer.create(context, R.raw.alarm)
        mediaPlayer.start()

        val text = intent.getStringExtra("TEXT")

        showNotification(context, text ?: "ini alarm")

    }

    // Notification (copy aja dari MainActivity)
    private fun showNotification(context: Context, text: String) {
        val idNotification = 2
        val idChannel = "channel-3"
        val channelName = "channel-alarm"

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(context, AlarmActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 1, intent, 0)

        // konfigurasi notifikasi
        val build = NotificationCompat.Builder(context, idChannel)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Ini content title")
            .setContentText(text)
            .setSubText("Ini sub text")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(idChannel, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        // show
        notificationManager.notify(idNotification, build.build())
    }
    //-------------------------------------------
    // Setelah membuat AlarmReceiver, sekarang bikin new Activity "TextToSpeech Activity" ->
}