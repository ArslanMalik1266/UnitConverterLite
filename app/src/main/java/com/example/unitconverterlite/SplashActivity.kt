package com.example.unitconverterlite

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.unitconverterlite.utils.LanguagePreferences
import com.example.unitconverterlite.utils.LocaleHelper
import com.example.unitconverterlite.utils.ThemePreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class SplashActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {

        val themePrefs = ThemePreferences(this)
        runBlocking {
            val isDark = themePrefs.isDarkMode.first()
            AppCompatDelegate.setDefaultNightMode(
                if (isDark) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        window.decorView.apply {
            window.insetsController?.let { controller ->
                controller.hide(android.view.WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior =
                    android.view.WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }

        window.decorView.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)

    }
}