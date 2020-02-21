package com.studiomasteguh.nontonkuy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.studiomasteguh.nontonkuy.home.dashboard.PlaysAdapter
import com.studiomasteguh.nontonkuy.home.model.Film
import com.studiomasteguh.nontonkuy.home.model.Plays
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {

    lateinit var mDatabase : DatabaseReference
    private var dataList = ArrayList<Plays>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        val data = intent.getParcelableExtra<Film>("data")

        mDatabase = FirebaseDatabase.getInstance().getReference("Film")
                .child(data.judul.toString())
                .child("play")

        tv_name_poster.text = data.judul
        tv_genre.text = data.genre
        tv_poster_desc.text = data.judul
        tv_rate.text = data.rating

        Glide.with(this).load(data.poster).into(img_poster)

        btn_pilih_bangku.setOnClickListener {
            val intent = Intent(this, PilihBangkuActivity::class.java).putExtra("data" , data)
            startActivity(intent)
        }

        img_back.setOnClickListener {
            finish()
        }

        rv_who_played.layoutManager = LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL, false)
        getData()

    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataList.clear()
                for (getdataSnapshot in dataSnapshot.getChildren()) {

                    val film = getdataSnapshot.getValue(Plays::class.java!!)
                    dataList.add(film!!)
                }

                rv_who_played.adapter = PlaysAdapter(dataList) {

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DetailMovieActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }

}


