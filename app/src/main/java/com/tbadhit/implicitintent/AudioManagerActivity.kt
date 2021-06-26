package com.tbadhit.implicitintent

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tbadhit.implicitintent.databinding.ActivityAudioManagerBinding

// Bikin layout (Copas aja) ->
class AudioManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAudioManagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1.
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        binding.btnRing.setOnClickListener {
            audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
            Toast.makeText(this@AudioManagerActivity, "mode normal", Toast.LENGTH_SHORT).show()

        }
        binding.btnSilent.setOnClickListener {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !notificationManager.isNotificationPolicyAccessGranted) {
                val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                startActivity(intent)
                return@setOnClickListener
            }
            audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT
            Toast.makeText(this@AudioManagerActivity, "mode silent", Toast.LENGTH_SHORT).show()
        }
        binding.btnVibrate.setOnClickListener {
            audioManager.ringerMode = AudioManager.RINGER_MODE_VIBRATE
            Toast.makeText(this@AudioManagerActivity, "mode vibrate", Toast.LENGTH_SHORT).show()
        }
        //-----------
        // Jangan lupa manifestnya di tambahin yg ada policynya ->
    }
}