package com.tbadhit.implicitintent

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tbadhit.implicitintent.databinding.ActivityCameraBinding


// Bikin Layout (Copas aja) ->
class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        checkPermission()
        newCheckPermissions()

        val pickImageGalery = registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            if (it != null) {
                binding.imgShow.setImageURI(it)
            } else {
                binding.imgShow.setImageResource(R.mipmap.ic_launcher)
            }
        }

        binding.btnToGaleri.setOnClickListener {
            pickImageGalery.launch(arrayOf("image/png", "image/jpeg"))
        }

        val pickImageCamera = registerForActivityResult(TakePicturePreview()) {
            if (it != null) {
                binding.imgShow.setImageBitmap(it)
            } else {
                binding.imgShow.setImageResource(R.mipmap.ic_launcher)
            }
        }

        binding.btnToCamera.setOnClickListener {
            pickImageCamera.launch(null)
        }


    }

    private fun newCheckPermissions() {
        val arrPermission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this@CameraActivity, "Permission Telah di Aktifkan", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(this, arrPermission, 150)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 150) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@CameraActivity, "Permission baru saja di aktifkan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@CameraActivity, "Permission tidak di aktifkan", Toast.LENGTH_SHORT).show()

                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
        }
    }

//    private fun checkPermission() {
//        val permissions = arrayOf<String>(
//            Manifest.permission.CAMERA,
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//        )
//        val rationale = "Please provide location permission so that you can ..."
//        val options: Permissions.Options = Permissions.Options()
//            .setRationaleDialogTitle("Info")
//            .setSettingsDialogTitle("Warning")
//
//        Permissions.check(
//            this /*context*/,
//            permissions,
//            rationale,
//            options,
//            object : PermissionHandler() {
//                override fun onGranted() {
//                    Toast.makeText(this@CameraActivity, "Permissions diaktifkan", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onDenied(context: Context?, deniedPermissions: ArrayList<String?>?) {
//                    Toast.makeText(this@CameraActivity, "Permissions diaktifkan", Toast.LENGTH_SHORT).show()
//                }
//            })
//
//    }
}