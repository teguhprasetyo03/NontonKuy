package com.studiomasteguh.nontonkuy.signin.signup

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import com.studiomasteguh.nontonkuy.home.HomeActivity
import com.studiomasteguh.nontonkuy.R
import com.studiomasteguh.nontonkuy.utils.Preferences
import kotlinx.android.synthetic.main.activity_sign_up.tv_hello_user
import kotlinx.android.synthetic.main.activity_sign_up_photo.*
import java.util.*

class SignUpPhotoActivity : AppCompatActivity(), PermissionListener {

    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd:Boolean = false
    lateinit var filePath: Uri

    lateinit var storage: FirebaseStorage
    lateinit var storageReference: StorageReference
    lateinit var preferences: Preferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photo)


        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        tv_hello_user.text = "Selamat Datang\n"+intent.getStringExtra("nama")

        img_add.setOnClickListener {
            if (statusAdd) {
                statusAdd = false
                btn_save.visibility = View.INVISIBLE
                img_add.setImageResource(R.drawable.ic_icon_add)
                img_profile.setImageResource(R.drawable.pic_profile)

            } else {
//                Dexter.withActivity(this)
//                        .withPermission(Manifest.permission.CAMERA)
//                        .withListener(this)
//                        .check()
                ImagePicker.with(this)
                        .cameraOnly()	//User can only capture image using Camera
                        .start()
            }
        }

        btn_daftar_sign_up_photo.setOnClickListener {

            finishAffinity()

            val intent = Intent(this@SignUpPhotoActivity,
                    HomeActivity::class.java)
            startActivity(intent)
        }

        btn_save.setOnClickListener {
            if (filePath != null) {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                val ref = storageReference.child("images/" + UUID.randomUUID().toString())
                ref.putFile(filePath)
                        .addOnSuccessListener {
                            progressDialog.dismiss()
                            Toast.makeText(this@SignUpPhotoActivity, "Uploaded", Toast.LENGTH_SHORT).show()

                            ref.downloadUrl.addOnSuccessListener {
                                preferences.setValues("url", it.toString())
                            }

                            finishAffinity()
                            val intent = Intent(this@SignUpPhotoActivity,
                                    HomeActivity::class.java)
                            startActivity(intent)

                        }
                        .addOnFailureListener { e ->
                            progressDialog.dismiss()
                            Toast.makeText(this@SignUpPhotoActivity, "Failed " + e.message, Toast.LENGTH_SHORT)
                                    .show()
                        }
                        .addOnProgressListener { taskSnapshot ->
                            val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                                    .totalByteCount
                            progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                        }
            }

        }
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        //To change body of created functions use File | Settings | File Templates.
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }

    }

    override fun onPermissionRationaleShouldBeShown(
            permission: com.karumi.dexter.listener.PermissionRequest?,
            token: PermissionToken?
    ) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        //To change body of created functions use File | Settings | File Templates.
        Toast.makeText(this, "Anda tidak bisa menambahkan photo profile", Toast.LENGTH_LONG ).show()
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Tergesah? Klik tombol Upload Nanti aja", Toast.LENGTH_LONG ).show()
    }

//    @SuppressLint("MissingSuperCall")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            var bitmap = data?.extras?.get("data") as Bitmap
//            statusAdd = true
//
//            filePath = data.getData()!!
//            Glide.with(this)
//                    .load(bitmap)
//                    .apply(RequestOptions.circleCropTransform())
//                    .into(img_profile)
//
//            btn_save.visibility = View.VISIBLE
//            img_add.setImageResource(R.drawable.ic_delete)
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            statusAdd = true
            filePath = data?.data!!

            Glide.with(this)
                    .load(filePath)
                    .apply(RequestOptions.circleCropTransform())
                    .into(img_profile)

            btn_save.visibility = View.VISIBLE
            img_add.setImageResource(R.drawable.ic_delete)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}
