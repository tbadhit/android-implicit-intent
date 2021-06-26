package com.tbadhit.implicitintent

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tbadhit.implicitintent.databinding.ActivityWifiBinding

// Bikin layout(Copas aja) ->
class WifiActivity : AppCompatActivity() {
    // (BD)
    private lateinit var binding: ActivityWifiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // (BS)
        binding = ActivityWifiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1.
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (wifiManager.isWifiEnabled) {
            binding.swWifi.isChecked = true
            binding.tvStatus.text = "Wifi telah aktif"
        } else {
            binding.swWifi.isChecked = false
            binding.tvStatus.text = "Wifi belum aktif"
        }

        binding.swWifi.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                wifiManager.isWifiEnabled = true;
                binding.tvStatus.text = "Wifi diaktifkan"
            } else {
                wifiManager.isWifiEnabled = false;
                binding.tvStatus.text = "Wifi dimatikan"
            }
        }
        // Jangan lupa tambahkan permission di manifestnya ->
        //-----------
        // Setelah bikin wifi next bikin Notification pergi ke "MainActivity" ->
    }
}