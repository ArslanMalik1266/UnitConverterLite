package com.example.unitconverterlite.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.unitconverterlite.Adaptor.PercentagePagerAdapter
import com.example.unitconverterlite.MainActivity
import com.example.unitconverterlite.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UnitConverterFragment : Fragment() {

    private lateinit var valueOne: EditText
    private lateinit var valueTwo: EditText
    internal var activeEditText: EditText? = null

    private val unitsMap = mapOf(
        "Length" to listOf("Meters", "Kilometers", "Miles", "Feet", "Inches"),
        "Area" to listOf("Square Meter", "Square Kilometer", "Square Mile", "Hectare", "Acre"),
        "Time" to listOf("Seconds", "Minutes", "Hours", "Days", "Weeks"),
        "Volume" to listOf("Liter", "Milliliter", "Gallon", "Cubic Meter", "Cubic Feet"),
        "Temperature" to listOf("Celsius", "Fahrenheit", "Kelvin"),
        "Weight" to listOf("Kilograms", "Grams", "Pounds", "Ounces"),
        "Speed" to listOf("m/s", "km/h", "mph", "ft/s"),
        "Energy" to listOf("Joule", "Calorie", "kWh", "BTU"),
        "Power" to listOf("Watt", "Kilowatt", "Horsepower"),
        "Torque" to listOf("Nm", "kg·m", "lb·ft"),
        "Pressure" to listOf("Pa", "kPa", "Bar", "psi"),
        "Force" to listOf("Newton", "Kilogram-force", "Pound-force"),
        "Angle" to listOf("Degree", "Radian", "Gradian"),
            "Currency" to listOf("USD", "EUR", "GBP", "JPY", "PKR"),
        "Sound" to listOf("Decibel"),
        "BMI" to listOf("Metric", "Imperial")
    )

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.showBottomNav(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_unit_converter, container, false)
        setupKeyboard(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<MaterialToolbar>(R.id.unitConverter_appBar)
        val cardTitle = arguments?.getString("title") ?: "Unit Converter"
        val cardType = arguments?.getString("type") ?: "Length"
        toolbar.title = cardTitle

        val constraintOne = view.findViewById<ConstraintLayout>(R.id.constraint_one)
        val layoutPercentage = view.findViewById<ConstraintLayout>(R.id.percentage_UI)
        val ratioUI = view.findViewById<ConstraintLayout>(R.id.ratio_UI)
        val bmiUI = view.findViewById<ConstraintLayout>(R.id.bmi_UI)


        constraintOne.visibility = View.GONE
        layoutPercentage.visibility = View.GONE

        when (cardType) {
            "Percentage" -> {
                layoutPercentage.visibility = View.VISIBLE
                setupPercentageUI(view)
            }

            "Ratio" -> {
                ratioUI.visibility = View.VISIBLE
                setupRatioUI(view)
            }
            "BMI" -> {
                bmiUI.visibility = View.VISIBLE
                setupBMIUI(view)
            }

            else -> {
                constraintOne.visibility = View.VISIBLE
                setupRegularUI(view, cardType)
            }
        }

        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        setCopyButtonEnabled(false)
    }

    private fun setupRegularUI(view: View, cardType: String) {
        val spinnerOne = view.findViewById<Spinner>(R.id.value_one_spinner)
        val spinnerTwo = view.findViewById<Spinner>(R.id.value_two_spinner)

        val units = unitsMap[cardType] ?: listOf("Unit1", "Unit2")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, units)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerOne.adapter = adapter
        spinnerTwo.adapter = adapter

        valueOne = view.findViewById(R.id.value_one)
        valueTwo = view.findViewById(R.id.value_two)

        valueOne.requestFocus()
        activeEditText = valueOne
        showKeyboard(true)

        valueOne.showSoftInputOnFocus = false
        valueTwo.showSoftInputOnFocus = false

        valueOne.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                activeEditText = valueOne
                showKeyboard(true)
                setCopyButtonEnabled(false)
            }
        }
        valueTwo.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                activeEditText = valueTwo
                showKeyboard(true)
                setCopyButtonEnabled(false)
            }
        }
    }

    private fun setupPercentageUI(view: View) {
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.nunito_semi_bold)
        val tabLayout = view.findViewById<TabLayout>(R.id.percentage_tab_layout)
        val viewPager = view.findViewById<ViewPager2>(R.id.percentage_view_pager)

        val adapter = PercentagePagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when(position) {
                0 -> "% Of"
                1 -> "Add"
                2 -> "Subtract"
                else -> ""
            }
        }.attach()

        for (i in 0 until tabLayout.tabCount) {
            val tab = (tabLayout.getChildAt(0) as ViewGroup).getChildAt(i) as ViewGroup
            for (j in 0 until tab.childCount) {
                val tabViewChild = tab.getChildAt(j)
                if (tabViewChild is androidx.appcompat.widget.AppCompatTextView) {
                    tabViewChild.typeface = typeface
                    tabViewChild.textSize = 16f
                }
            }
        }
    }

    private fun setupKeyboard(view: View) {
        val gridLayout: GridLayout = view.findViewById(R.id.keyoboard_grid_layout)
        val buttons = listOf(
            "7", "8", "9",
            "4", "5", "6",
            "1", "2", "3",
            ".", "0", "⌫"
        )

        gridLayout.rowCount = 4
        gridLayout.columnCount = 3

        buttons.forEachIndexed { index, label ->
            val button = MaterialButton(requireContext()).apply {
                text = label
                textSize = 36f
                setTextColor(resources.getColor(R.color.text_grey))
                setBackgroundResource(R.drawable.keyboard_key_ripple)
                backgroundTintList = null
                typeface = ResourcesCompat.getFont(requireContext(), R.font.inter_regular)
            }

            val param = GridLayout.LayoutParams(
                GridLayout.spec(index / 3, 1f),
                GridLayout.spec(index % 3, 1f)
            ).apply {
                width = 0
                height = 0
                setMargins(4, 4, 4, 4)
            }
            button.layoutParams = param

            button.setOnClickListener {
                activeEditText?.let { edit ->
                    when (label) {
                        "⌫" -> { // Backspace
                            val start = edit.selectionStart
                            val end = edit.selectionEnd
                            if (start > 0) {
                                edit.text.replace(start - 1, end, "")
                                edit.setSelection(start - 1)
                            }
                        }
                        else -> {
                            val start = edit.selectionStart
                            edit.text.insert(start, label)
                            edit.setSelection(start + label.length)
                        }
                    }
                }
            }

            gridLayout.addView(button)
        }
    }

    private fun setCopyButtonEnabled(enabled: Boolean) {
        val copyButton = view?.findViewById<Button>(R.id.copy_result_btn)
        copyButton?.isEnabled = enabled
        if (enabled) {
            copyButton?.setBackgroundColor(resources.getColor(R.color.blue_color))
            copyButton?.setTextColor(resources.getColor(R.color.white))
        } else {
//            copyButton?.setBackgroundColor(resources.getColor(R.color.light_grey))
//            copyButton?.setTextColor(resources.getColor(R.color.text_grey))
            copyButton?.setBackgroundColor(resources.getColor(R.color.blue_color))
            copyButton?.setTextColor(resources.getColor(R.color.white))
        }
    }

    internal fun showKeyboard(show: Boolean) {
        val keyboard = view?.findViewById<GridLayout>(R.id.keyoboard_grid_layout) ?: return
        if (show) {
            keyboard.animate().translationY(0f).setDuration(300).start()
        } else {
            keyboard.animate().translationY(keyboard.height.toFloat()).setDuration(300).start()
        }
    }

    private fun setupRatioUI(view: View) {

        val xValue = view.findViewById<EditText>(R.id.x_value)
        val yValue = view.findViewById<EditText>(R.id.y_value)
        val resultValue = view.findViewById<EditText>(R.id.result_value)

        // Disable system keyboard
        xValue.showSoftInputOnFocus = false
        yValue.showSoftInputOnFocus = false
        resultValue.showSoftInputOnFocus = false

        // Initial focus
        xValue.requestFocus()
        activeEditText = xValue
        showKeyboard(true)

        xValue.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                activeEditText = xValue
                showKeyboard(true)
            }
        }

        yValue.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                activeEditText = yValue
                showKeyboard(true)
            }
        }

        resultValue.isFocusable = false
        resultValue.isClickable = false
    }

    private fun setupBMIUI(view: View) {

        val feetEt = view.findViewById<EditText>(R.id.feet_value)
        val inchesEt = view.findViewById<EditText>(R.id.inches_value)
        val weightEt = view.findViewById<EditText>(R.id.weight_value)
        val resultEt = view.findViewById<EditText>(R.id.result_bmi_value)

        // Disable system keyboard (important for your custom keyboard)
        feetEt.showSoftInputOnFocus = false
        inchesEt.showSoftInputOnFocus = false
        weightEt.showSoftInputOnFocus = false
        resultEt.showSoftInputOnFocus = false

        // Set active edit text on focus
        feetEt.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                activeEditText = feetEt
                showKeyboard(true)
            }
        }

        inchesEt.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                activeEditText = inchesEt
                showKeyboard(true)
            }
        }

        weightEt.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                activeEditText = weightEt
                showKeyboard(true)
            }
        }

        fun calculateBMI() {
            val feet = feetEt.text.toString().toDoubleOrNull() ?: 0.0
            val inches = inchesEt.text.toString().toDoubleOrNull() ?: 0.0
            val weightKg = weightEt.text.toString().toDoubleOrNull() ?: 0.0

            if (feet == 0.0 && inches == 0.0 || weightKg == 0.0) {
                resultEt.setText("")
                return
            }

            // Convert height to meters
//            val totalInches = (feet * 12) + inches
//            val heightMeters = totalInches * 0.0254
//
//            if (heightMeters == 0.0) return
//
//            val bmi = weightKg / (heightMeters * heightMeters)

//            val category = when {
//                bmi < 18.5 -> "Underweight"
//                bmi < 25 -> "Normal"
//                bmi < 30 -> "Overweight"
//                else -> "Obese"
//            }

//            resultEt.setText(String.format("%.2f (%s)", bmi, category))
        }

        feetEt.requestFocus()
        activeEditText = feetEt
        showKeyboard(true)
    }


}
