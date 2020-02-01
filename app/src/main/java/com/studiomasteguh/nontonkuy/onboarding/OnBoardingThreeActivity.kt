package com.studiomasteguh.nontonkuy.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.studiomasteguh.nontonkuy.R
import com.studiomasteguh.nontonkuy.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_on_boarding_three.*

class OnBoardingThreeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_three)

        // jika kita mengklik lewati perkenalan
        btn_home_ob3.setOnClickListener {
            finishAffinity()

            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}
