package com.studiomasteguh.nontonkuy.signin.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import com.studiomasteguh.nontonkuy.R
import com.studiomasteguh.nontonkuy.signin.signin.User
import com.studiomasteguh.nontonkuy.utils.Preferences
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    lateinit var signUsername : String
    lateinit var signPassword : String
    lateinit var signNama : String
    lateinit var signEmail : String

    private lateinit var mFirebaseDatabase : DatabaseReference
    private lateinit var mFirebaseInstance : FirebaseDatabase
    private lateinit var mDatabase : DatabaseReference

    private lateinit var preferences : Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mFirebaseDatabase = mFirebaseInstance.getReference("User")

        preferences = Preferences(this)

        btn_daftar.setOnClickListener {
            signUsername = edt_username.text.toString()
            signPassword = edt_password.text.toString()
            signNama     = edt_nama.text.toString()
            signEmail    = edt_email.text.toString()

            if (signUsername.equals("")) {
                edt_username.error = "Silahkan isi username anda"
                edt_username.requestFocus()
            } else if ( signPassword.equals("")) {
                edt_password.error = "Silahkan isi password anda"
                edt_password.requestFocus()
            }  else if ( signNama.equals("")) {
                edt_nama.error = "Silahkan isi nama anda"
                edt_nama.requestFocus()
            }  else if ( signEmail.equals("")) {
                edt_email.error = "Silahkan isi email anda"
                edt_email.requestFocus()
            } else {
                saveUser(signUsername, signNama, signPassword, signEmail)
            }
        }
    }

    private fun saveUser(signUsername:String, signPassword:String
                         , signNama:String , signEmail : String){

        val user = User()
        user.email = signEmail
        user.nama = signNama
        user.username = signUsername
        user.password = signPassword

        if (signUsername != null){
            checkingUsername(signUsername, user)
        }
    }

    private fun checkingUsername(Username: String, data: User) {
        mFirebaseDatabase.child(Username).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue(User::class.java)
                if (user == null){
                    mFirebaseDatabase.child(Username).setValue(data)

                    preferences.setValues("nama", data.nama.toString())
                    preferences.setValues("user", data.username.toString())
                    preferences.setValues("url", data.url.toString())
                    preferences.setValues("email", data.email.toString())
                    preferences.setValues("status", "1")

                    // berpindah dari activity sign up ke sign up photo
                    val intent = Intent(this@SignUpActivity,
                            SignUpPhotoActivity::class.java).putExtra("nama", data.nama)
                    startActivity(intent)

                } else{
                    Toast.makeText(this@SignUpActivity, "User Sudah Pernah Digunakan", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUpActivity, ""+ error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
