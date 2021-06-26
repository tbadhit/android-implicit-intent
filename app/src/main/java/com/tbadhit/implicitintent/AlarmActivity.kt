package com.tbadhit.implicitintent

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tbadhit.implicitintent.databinding.ActivityAlarmBinding
import java.util.*

// Bikin Layout(Copas aja) ->
class AlarmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTimePicker.setOnClickListener {
            setAlarmTime()
        }
    }

    // 1.
    private fun setAlarmTime() {
        val calendar = Calendar.getInstance()

        val timePickerDialog =
            TimePickerDialog(this@AlarmActivity, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

                // Membuat receiver function ketika jam sudah di set :
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(this@AlarmActivity, AlarmReceiver::class.java)
                intent.putExtra("TEXT", "Bangunn!!")

                val pendingIntent = PendingIntent.getBroadcast(this@AlarmActivity, 1, intent, 0)

                val cal = Calendar.getInstance()
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)
                cal.set(Calendar.SECOND, 0)

                alarmManager.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent)

                binding.tvSetAlarm.text = "Alarm diatur untuk jam ${cal.time}"

            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)

        timePickerDialog.show()
    }
    // Setelah bikin setAlarmTime, next bikin class "AlarmRecaiver"
    // cara bikinnya ke new/other/broadcase receiver
}