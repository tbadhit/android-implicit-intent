package com.tbadhit.implicitintent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.os.StrictMode
import android.provider.ContactsContract
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PickContact : ActivityResultContract<Int, Uri?>() {
    override fun createIntent(context: Context, input: Int?) =
        Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI).also {
            it.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return if (resultCode == Activity.RESULT_OK) intent?.data else null
    }
}

class TakePicturePreview : ActivityResultContract<Void, Bitmap>() {

    lateinit var lokasiGambar: Uri

    override fun createIntent(context: Context, input: Void?): Intent {
        // konfigurasi folder
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().build())

        // Atur folder :
        val namaFolder = "camera-kotlin-intermediate"
        val path = Environment.getExternalStorageDirectory().absolutePath
        val folder = File(path, namaFolder)
        if (!folder.exists()) {
            folder.mkdir()
        }

        val namaFile = File("$path/$namaFolder/image-${currentDate()}.jpg")
        lokasiGambar = Uri.fromFile(namaFile)

        // Buka Kamera :
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, lokasiGambar)
        return intent
    }

    //    Membuat fungsi tanggal sekarang untuk nama file gambarnya biar tidak sama :
    private fun currentDate(): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy_HH:mm:ss", Locale.getDefault())
        val data = Date()
        return sdf.format(data)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Bitmap? {
        return if (intent == null || resultCode != Activity.RESULT_OK) null else intent.getParcelableExtra(
            "data"
        )!!
    }

}