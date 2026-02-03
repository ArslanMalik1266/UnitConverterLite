package com.example.unitconverterlite.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.lifecycleScope
import com.example.unitconverterlite.MainActivity
import com.example.unitconverterlite.R
import com.example.unitconverterlite.utils.AppPreferences
import com.example.unitconverterlite.utils.ThemePreferences
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.radiobutton.MaterialRadioButton
import kotlinx.coroutines.launch


class SettingsFragment : Fragment() {

    private lateinit var mode_swtich: SwitchCompat
    private lateinit var version: TextView
    private lateinit var build: TextView
    private lateinit var latest_update: TextView

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.showBottomNav(true)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val themePrefs = ThemePreferences(requireContext())
        val appPrefs = AppPreferences(requireContext())
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)
        val noDecimalRadio = view.findViewById<MaterialRadioButton>(R.id.no_decimal_radio)
        val twoDecimalRadio = view.findViewById<MaterialRadioButton>(R.id.two_decimal_radio)
        val fourDecimalRadio = view.findViewById<MaterialRadioButton>(R.id.four_decimal_radio)
        version = view.findViewById<TextView>(R.id.version_value_tv)
        build = view.findViewById<TextView>(R.id.build_value_tv)
        latest_update = view.findViewById<TextView>(R.id.latest_update_value_tv)


        version_detail()
        lifecycleScope.launch {
            appPrefs.decimalPrecisionFlow.collect { savedValue ->
                when (savedValue) {
                    0 -> noDecimalRadio.isChecked = true
                    2 -> twoDecimalRadio.isChecked = true
                    4 -> fourDecimalRadio.isChecked = true
                }
            }
        }
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedValue = when (checkedId) {
                R.id.no_decimal_radio -> 0
                R.id.two_decimal_radio -> 2
                R.id.four_decimal_radio -> 4
                else -> 2
            }
            lifecycleScope.launch {
                appPrefs.saveDecimalPrecision(selectedValue)
            }

        }


        mode_swtich = view.findViewById<SwitchCompat>(R.id.mode_swtich)
        val autosaveSwitch = view.findViewById<SwitchCompat>(R.id.autosave_swtich)

        lifecycleScope.launch {
            appPrefs.isAutoSaveEnabled.collect { enabled ->
                if (autosaveSwitch.isChecked != enabled) {
                    autosaveSwitch.isChecked = enabled
                }
            }
        }

        autosaveSwitch.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                appPrefs.saveAutoSave(isChecked)

            }
        }


        lifecycleScope.launch {
            themePrefs.isDarkMode.collect { isDark ->
                if (mode_swtich.isChecked != isDark) {
                    mode_swtich.isChecked = isDark
                }
            }
        }

        mode_swtich.setOnCheckedChangeListener { _, isChecked ->

            lifecycleScope.launch {
                themePrefs.saveDarkMode(isChecked)
                kotlinx.coroutines.delay(200)

                AppCompatDelegate.setDefaultNightMode(
                    if (isChecked)
                        AppCompatDelegate.MODE_NIGHT_YES
                    else
                        AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }


        val toolbar = view.findViewById<MaterialToolbar>(R.id.topAppBar)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }


    private fun version_detail() {
        val packageInfo =
            requireContext().packageManager.getPackageInfo(requireContext().packageName, 0)
        val versionName = packageInfo.versionName
        val lastUpdate = packageInfo.lastUpdateTime
        val lastUpdateDate = java.text.SimpleDateFormat("MMMM yyyy", java.util.Locale.getDefault())
            .format(java.util.Date(lastUpdate))

        latest_update.text = lastUpdateDate
        version.text = versionName
    }


}