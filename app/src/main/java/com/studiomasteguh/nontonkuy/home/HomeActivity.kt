package com.studiomasteguh.nontonkuy.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.studiomasteguh.nontonkuy.R
import com.studiomasteguh.nontonkuy.home.dashboard.DashBoardFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragmentTiket = TiketFragment()
        val settingFragment = SettingFragment()
        val fragmentHome = DashBoardFragment()

        setFragment(fragmentHome)

        img_menu1.setOnClickListener {
            setFragment(fragmentHome)

            changeIcon(img_menu1, R.drawable.ic_home_active)
            changeIcon(img_menu2, R.drawable.ic_tiket)
            changeIcon(img_menu3, R.drawable.ic_profil)
        }

        img_menu2.setOnClickListener {
            setFragment(fragmentTiket)

            changeIcon(img_menu1, R.drawable.ic_home)
            changeIcon(img_menu2, R.drawable.ic_tiket_active)
            changeIcon(img_menu3, R.drawable.ic_profil)
        }

        img_menu3.setOnClickListener {
            setFragment(settingFragment)

            changeIcon(img_menu1, R.drawable.ic_home)
            changeIcon(img_menu2, R.drawable.ic_tiket)
            changeIcon(img_menu3, R.drawable.ic_profil_active)
        }

    }

    private fun changeIcon(imageView: ImageView , int:Int) {
        imageView.setImageResource(int)

    }

    protected fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame, fragment)
        fragmentTransaction.commit()
    }

}
