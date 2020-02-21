package com.studiomasteguh.nontonkuy.home.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*

import com.studiomasteguh.nontonkuy.R
import com.studiomasteguh.nontonkuy.home.model.Film
import kotlinx.android.synthetic.main.fragment_dash_board.*
import java.text.NumberFormat
import java.util.*
import com.studiomasteguh.nontonkuy.utils.Preferences
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class DashBoardFragment : Fragment() {

    private lateinit var preferences: Preferences
    lateinit var mDatabase: DatabaseReference

    private var dataList = ArrayList<Film>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dash_board, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(activity!!.applicationContext)
        mDatabase = FirebaseDatabase.getInstance().getReference("Film")

        tv_nama.setText(preferences.getValues("nama"))
        if (!preferences.getValues("saldo").equals("")) {
            currecy(preferences.getValues("saldo")!!.toDouble(), tv_saldo)
        }

        Glide.with(this)
                .load(preferences.getValues("url"))
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile)

        rv_nowplaying.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_comingsoon.layoutManager = LinearLayoutManager(context!!.applicationContext)
        getData()

    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataList.clear()
                for (getdataSnapshot in dataSnapshot.getChildren()) {

                    val film = getdataSnapshot.getValue(Film::class.java!!)
                    dataList.add(film!!)
                }

                rv_nowplaying.adapter = NowPlayingAdapter(dataList) {
                    val intent = Intent(context,
                            DetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }

                rv_comingsoon.adapter = ComingSoonAdapter(dataList) {
                    val intent = Intent(context,
                            DetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "" + error.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun currecy(harga: Double, textView: TextView) {
        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        textView.setText(formatRupiah.format(harga as Double))

    }
}