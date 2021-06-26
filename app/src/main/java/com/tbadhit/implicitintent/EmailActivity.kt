package com.tbadhit.implicitintent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tbadhit.implicitintent.databinding.ActivityEmailBinding

// Bikin Layout ->
class EmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1.
        binding.btnKirimEmail.setOnClickListener {
            val emailTujuan = binding.edtEmailTujuan.text.toString()
            val subject = binding.edtSubject.text.toString()
            val bodyEmail = binding.edtBodyEmail.text.toString()

            if (emailTujuan.isBlank() || subject.isBlank() || bodyEmail.isBlank()) {
                Toast.makeText(this@EmailActivity, "Tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailTujuan))
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, bodyEmail)
            startActivity(intent)
        }
        //------
        // Setelah bikin kode di atas, skrng bikin activity baru "WifiActivity" ->
    }
}