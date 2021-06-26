package com.tbadhit.implicitintent

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.tbadhit.implicitintent.databinding.ActivityMainBinding

// Pertama: Copas Layout (pergi ke activity_main.xml -> )
class MainActivity : AppCompatActivity() {

    // (BD)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPhone.setOnClickListener {
            startActivity(Intent(this@MainActivity, PhoneActivity::class.java))
        }

        binding.btnSms.setOnClickListener {
            startActivity(Intent(this@MainActivity, SmsActivity::class.java))
        }

        binding.btnEmail.setOnClickListener {
            startActivity(Intent(this@MainActivity, EmailActivity::class.java))
        }
        binding.btnWifi.setOnClickListener {
            startActivity(Intent(this@MainActivity, WifiActivity::class.java))
        }
        binding.btnNotifikasi.setOnClickListener {
            showNotification()
        }
        binding.btnAlarm.setOnClickListener {
            startActivity(Intent(this@MainActivity, AlarmActivity::class.java))
        }
        binding.btnTts.setOnClickListener {
            startActivity(Intent(this@MainActivity, TextToSpeechActivity::class.java))
        }
        binding.btnAudioManager.setOnClickListener {
            startActivity(Intent(this@MainActivity, AudioManagerActivity::class.java))
        }
        binding.btnOtherApp.setOnClickListener {
            val intent = packageManager.getLaunchIntentForPackage("com.gojek.app")

            if (intent != null) {
                startActivity(intent)
            } else {
                val playStore = Intent(Intent.ACTION_VIEW)
                playStore.data = Uri.parse("https://play.google.com/store/apps/details?id=com.gojek.app")
                startActivity(playStore)
            }
        }
        // Setelah membaut btnOtherApp, bikin "CameraActivity" ->
        binding.btnCamera.setOnClickListener {
            startActivity(Intent(this@MainActivity, CameraActivity::class.java))
        }


    }
    //-----------
    // Setelah bikin binding bikin "PhoneActivity" ->


    // Notification
    private fun showNotification() {
        val idNotification = 1
        val idChannel = "channel-1"
        val channelName = "channel-kotlin-intermediate"

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.idn.id"))
        val pendingIntent = PendingIntent.getActivity(this, 1, intent, 0)

        // konfigurasi notifikasi
        val build = NotificationCompat.Builder(this, idChannel)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Ini content title")
            .setContentText("Ini Content Text")
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
    // Setelah bikin notifikasi bikin Alarm
}