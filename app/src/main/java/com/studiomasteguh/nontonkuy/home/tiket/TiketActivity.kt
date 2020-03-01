package com.studiomasteguh.nontonkuy.home.tiket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.studiomasteguh.nontonkuy.R
import com.studiomasteguh.nontonkuy.checkout.model.Checkout
import com.studiomasteguh.nontonkuy.home.model.Film
import kotlinx.android.synthetic.main.activity_tiket.*
import kotlinx.android.synthetic.main.alert_qr_code.view.*
import kotlinx.android.synthetic.main.fragment_tiket.*

class TiketActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiket)

        val data = intent.getParcelableExtra<Film>("data")

        tv_title.text = data.judul
        tv_genre.text = data.genre
        tv_rate.text = data.rating

        Glide.with(this)
                .load(data.poster)
                .into(iv_poster_image)

        rc_checkout.layoutManager = LinearLayoutManager(this)
        dataList.add(Checkout("C1", ""))
        dataList.add(Checkout("C2", ""))

        rc_checkout.adapter = TiketAdapter(dataList) {


        }

        img_back.setOnClickListener {
            finish()
        }

        img_qr_tiket.setOnClickListener {
            AlertDialog()
        }

        img_qr_tiket_buttom.setOnClickListener {
            AlertDialog()
        }
    }

    private fun AlertDialog() {
        //Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.alert_qr_code, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
        //show dialog
        val mAlertDialog = mBuilder.show()

        mDialogView.btn_qr_code.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
        }
    }
}
