package com.studiomasteguh.nontonkuy.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.studiomasteguh.nontonkuy.R
import kotlinx.android.synthetic.main.activity_on_boarding_two.*

class OnBoardingTwoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_two)

        // pindah ke activity on boarding 3
        btn_home_ob2.setOnClickListener {
            val intent = Intent(this, OnBoardingThreeActivity::class.java)
            startActivity(intent)
        }
    }
}
