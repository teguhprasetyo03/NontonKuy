package com.studiomasteguh.nontonkuy.checkout

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.media.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.studiomasteguh.nontonkuy.R
import com.studiomasteguh.nontonkuy.checkout.adapter.CheckoutAdapter
import com.studiomasteguh.nontonkuy.checkout.model.Checkout
import com.studiomasteguh.nontonkuy.home.model.Film
import com.studiomasteguh.nontonkuy.home.tiket.TiketActivity
import kotlinx.android.synthetic.main.activity_checkout.*
import java.text.NumberFormat
import java.util.*
import com.studiomasteguh.nontonkuy.utils.Preferences
import kotlin.collections.ArrayList

class CheckoutActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()
    private var total: Int = 0

    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        preferences = Preferences(this)
        dataList = intent.getSerializableExtra("data") as ArrayList<Checkout>
        val data = intent.getParcelableExtra<Film>("datas")


        for (a in dataList.indices) {
            total += dataList[a].harga!!.toInt()
        }

        dataList.add(Checkout("Total Harus Dibayar", total.toString()))

        btn_tiket.setOnClickListener {
            val intent = Intent(this@CheckoutActivity,
                    CheckoutSuccessActivity::class.java)
            startActivity(intent)

            ShowNotif(data)
        }

        rc_checkout.layoutManager = LinearLayoutManager(this)
        rc_checkout.adapter = CheckoutAdapter(dataList) {
        }

        img_back.setOnClickListener {
            finish()
        }

        btn_home.setOnClickListener {
            finish()
        }

        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        tv_saldo.setText(formatRupiah.format(preferences.getValues("saldo")!!.toDouble()))
    }

    private fun ShowNotif(datas:Film){
        val NOTIFICATION_CHANNEL_ID = "channel_nonton_kuy_notif"
        val context = this.applicationContext
        var notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            val channelName = "NONTONKUY  Notif Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val nChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance)
            notificationManager.createNotificationChannel(nChannel)
        }
//
////        val mIntent = Intent(this , CheckoutSuccessActivity::class.java)
////        val bundle =  Bundle()
////        bundle.putString("id","id_film")
//        mIntent.putExtras(bundle)


        val mIntent = Intent(this ,TiketActivity::class.java)
        val bundle =  Bundle()
        bundle.putParcelable("data",datas)
        mIntent.putExtras(bundle)

        val pendingIntent = PendingIntent.getActivity(this, 0,mIntent,PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = androidx.core.app.NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.icon_mov))
                .setTicker("notif nonton kuy started")
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setLights(Color.RED, 3000, 3000)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentTitle("Sukses Terbeli")
                .setContentText("Tiket " + datas.judul + " berhasil kamu beli . Enjoying Watch Movie" )

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(115 , builder.build())
    }
}


