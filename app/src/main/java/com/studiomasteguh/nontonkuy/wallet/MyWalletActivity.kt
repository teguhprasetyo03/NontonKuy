package com.studiomasteguh.nontonkuy.wallet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.studiomasteguh.nontonkuy.R
import com.studiomasteguh.nontonkuy.wallet.adapter.WalletAdapter
import com.studiomasteguh.nontonkuy.wallet.model.Wallet
import kotlinx.android.synthetic.main.activity_my_wallet.*

class MyWalletActivity : AppCompatActivity() {

    private var datalist = ArrayList<Wallet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet)

        datalist.add(
                Wallet(
                        "Avengers",
                        "Minggu , 25 Jan 2020",
                        5000.0,
                        "0"
                )
        )
        datalist.add(
                Wallet(
                        "Top Up",
                        "Minggu , 25 Jan 2020",
                        200000.0,
                        "1"
                )
        )
        datalist.add(
                Wallet(
                        "Habibie & Ainun 3",
                        "Minggu , 25 Jan 2020",
                        50000.0,
                        "0"
                )
        )
        rv_top_up.layoutManager = LinearLayoutManager(this)
        rv_top_up.adapter = WalletAdapter(datalist){

        }
    }
}
