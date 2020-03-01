package com.studiomasteguh.nontonkuy.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.studiomasteguh.nontonkuy.R
import com.studiomasteguh.nontonkuy.home.HomeActivity
import com.studiomasteguh.nontonkuy.home.model.Film
import com.studiomasteguh.nontonkuy.home.tiket.TiketActivity
import kotlinx.android.synthetic.main.activity_checkout_success.*

class CheckoutSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_success)

        btn_home.setOnClickListener {
            finishAffinity()

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }
}


