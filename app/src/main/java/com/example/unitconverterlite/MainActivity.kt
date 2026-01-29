package com.example.unitconverterlite

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.unitconverterlite.fragments.HistoryFragment
import com.example.unitconverterlite.fragments.Home
import com.example.unitconverterlite.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView : BottomNavigationView



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, Home())
            .commit()

        bottomNav()

    }



    private fun bottomNav (){
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, Home())
                        .commit()
                    true
                }
                R.id.nav_history -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, HistoryFragment())
                        .commit()
                    true
                }
                R.id.nav_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, SettingsFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }

    }

    fun showBottomNav(show: Boolean) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager
            .findFragmentById(R.id.main_container)

        if (currentFragment !is Home) {
            bottomNavigationView.selectedItemId = R.id.nav_home
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, Home())
                .commit()
        } else {
            super.onBackPressed()
        }
    }



}