package com.tbadhit.implicitintent

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tbadhit.implicitintent.databinding.ActivityPhoneBinding

// Setelah bikin class bikin layout(Copas aja) (pergi ke activity_phone.xml -> )
class PhoneActivity : AppCompatActivity() {

    // (PBD)
    private lateinit var binding: ActivityPhoneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // (PBD)
        binding = ActivityPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //-------------------------

        // 1.
        // Bikin class "PickContact" ->
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
                    binding.edtNoTelp.setText(noTel)
                }
            } else {
                binding.edtNoTelp.setText("")
                Toast.makeText(this, "kosong", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnPilihKontak.setOnClickListener {
            getContact.launch(null)

        }
        //------------------


        // 2
        binding.btnPanggil.setOnClickListener {
            val noTel = binding.edtNoTelp.text.toString()

            if (noTel.isBlank()) {
                binding.edtNoTelp.error = "nomor telpon harus di isi"
                return@setOnClickListener
            }

            if (ContextCompat.checkSelfPermission(this@PhoneActivity, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:$noTel")
                startActivity(intent)
            } else {
                ActivityCompat.requestPermissions(this@PhoneActivity, arrayOf(android.Manifest.permission.CALL_PHONE), 100)
            }

//            registerForActivityResult(ActivityResultContracts.RequestPermission()){
//                if (it){
//                    val intent = Intent(Intent.ACTION_CALL)
//                    intent.data = Uri.parse("tel:$noTel")
//                    startActivity(intent)
//                }
//                else{
//                    Toast.makeText(this, "Perizinan tidak diaktifkan, silahkan menuju setting", Toast.LENGTH_SHORT).show()
//                }
//            }.launch(Manifest.permission.CALL_PHONE)


        }
        //-----------------------------------------

        // 3.
        binding.btnDialPhone.setOnClickListener {
            val noTel = binding.edtNoTelp.text.toString()

            if (noTel.isBlank()) {
                binding.edtNoTelp.error = "nomor telpon harus di isi"
                return@setOnClickListener
            }

            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$noTel")
            startActivity(intent)
        }
        //----------
        // Setelah bikin kode yang di komen (BDP) skrng bikin class baru "SmsActivity" ->

    }

}