package com.studiomasteguh.nontonkuy.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.studiomasteguh.nontonkuy.R
import com.studiomasteguh.nontonkuy.signin.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_on_boarding_one.*

class OnBoardingOneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_one)

        // btn home berfungsi untuk berpindah dari
        // on boarding one ke on boarding two
        btn_home.setOnClickListener {
            val intent = Intent(this, OnBoardingTwoActivity::class.java)
            startActivity(intent)
        }

        // jika kita mengklik lewati perkenalan
        btn_daftar.setOnClickListener {
            finishAffinity()

            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}
