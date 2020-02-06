package com.studiomasteguh.nontonkuy.signin.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.studiomasteguh.nontonkuy.HomeActivity
import com.studiomasteguh.nontonkuy.R
import com.studiomasteguh.nontonkuy.signin.SignUpActivity
import com.studiomasteguh.nontonkuy.utils.Preferences
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    // deklarasi variabel username dan password
    lateinit var Username : String
    lateinit var Password : String

    lateinit var mDatabase : DatabaseReference
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")

        preferences = Preferences(this)

        preferences.setValues("onboarding", "1")
        if (preferences.getValues("status").equals("1")) {
            finishAffinity()

            val intent = Intent(this@SignInActivity,
                    HomeActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            Username = edt_username.text.toString()
            Password = edt_password.text.toString()

            if (Username.equals("")) {
                edt_username.error = "Silahkan isi username anda"
                edt_username.requestFocus()
            } else if (Password.equals("")) {
                edt_password.error = "Silahkan isi password anda"
                edt_password.requestFocus()
            } else {
                pushLogin(Username, Password)
            }
        }

        btn_daftar.setOnClickListener {
            val intent = Intent(this@SignInActivity,
                    SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun pushLogin(Username:String, Password : String){
        mDatabase.child(Username).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue(User::class.java)
                if (user == null){
                    Toast.makeText(this@SignInActivity, "User tidak ditemukan", Toast.LENGTH_SHORT).show()
                } else {
                    if (user.password.equals(Password)){
                        Toast.makeText(this@SignInActivity, "Selamat Datang", Toast.LENGTH_SHORT).show()
                        preferences.setValues("nama", user.nama.toString())
                        preferences.setValues("user", user.username.toString())
                        preferences.setValues("url", user.url.toString())
                        preferences.setValues("email", user.email.toString())
                        preferences.setValues("saldo", user.saldo.toString())
                        preferences.setValues("status", "1")

                        finishAffinity()

                        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    } else{
                        Toast.makeText(this@SignInActivity, "Password Anda Salah", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignInActivity, ""+error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
