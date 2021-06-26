package com.tbadhit.implicitintent

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tbadhit.implicitintent.databinding.ActivitySmsBinding

// Setelah bikin kelas skrng bikin layout (Copas aja) ->
class SmsActivity : AppCompatActivity() {

    // (BD)
    private lateinit var binding: ActivitySmsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // (BD)
        binding = ActivitySmsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //---------

        // 1.
        val getContact = registerForActivityResult(PickContact()) {

            if (it != null) {
                val cursor = contentResolver.query(
                    it,
                    arrayOf(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER,
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    ),
                    null,
                    null,
                    null
                )?.apply {
                    moveToNext()
                }

                if (cursor != null && cursor.moveToFirst()) {
                    val noTel = cursor.getString(2)
                    binding.edtNomorTeleponSms.setText(noTel)
                }
            } else {
                binding.edtNomorTeleponSms.setText("")
                Toast.makeText(this, "kosong", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnPilihKontakSms.setOnClickListener {
            getContact.launch(null)

        }
        //------------------


        // 2.
        binding.btnSmsIntent.setOnClickListener {
            val noTel = binding.edtNomorTeleponSms.text.toString()
            val message = binding.edtPesan.text.toString()

            if (noTel.isBlank() || message.isBlank()) {
                Toast.makeText(this, "nomor telpon dan pesan harus di isi", Toast.LENGTH_SHORT)
                    .show()
            }

            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("sms:$noTel")
            intent.putExtra("sms_body", message)
            startActivity(intent)
        }
        //-------------

        // 3.
        binding.btnKirimSms.setOnClickListener {
            val noTel = binding.edtNomorTeleponSms.text.toString()
            val message = binding.edtPesan.text.toString()

            if (noTel.isBlank() || message.isBlank()) {
                Toast.makeText(this, "nomor telpon dan pesan harus di isi", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (ContextCompat.checkSelfPermission(
                    this@SmsActivity,
                    Manifest.permission.SEND_SMS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val smsManager = SmsManager.getDefault()
                try {
                    smsManager.sendTextMessage(noTel, null, message, null, null)
                } catch (e: Exception) {
                    Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            } else {
                ActivityCompat.requestPermissions(
                    this@SmsActivity,
                    arrayOf(Manifest.permission.SEND_SMS), 100
                )
            }

        }
        //------------------
        // Setelah membuat kode yang di komen (BKS) sekarang buat activitybaru "EmailActivity"
    }
}