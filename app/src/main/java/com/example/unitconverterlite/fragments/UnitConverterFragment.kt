package com.example.unitconverterlite.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.unitconverterlite.Adaptor.PercentagePagerAdapter
import com.example.unitconverterlite.DataClass.HistoryItem
import com.example.unitconverterlite.MainActivity
import com.example.unitconverterlite.R
import com.example.unitconverterlite.utils.UnitConverter
import com.example.unitconverterlite.viewModel.HistoryViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UnitConverterFragment : Fragment() {

    private lateinit var valueOne: EditText
    private lateinit var valueTwo: EditText
    internal var activeEditText: EditText? = null
    private lateinit var equalButton: MaterialButton
    private lateinit var keyboard: GridLayout
    private lateinit var switchButton: ImageView
    private lateinit var value_one_spinner: Spinner
    private lateinit var value_two_spinner: Spinner
    private var copyButtons: List<Button> = emptyList()
    private lateinit var copyButton: MaterialButton
    private var isUpdating = false
    private lateinit var historyViewModel: HistoryViewModel

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

        // Toolbar setup
        val toolbar = view.findViewById<MaterialToolbar>(R.id.unitConverter_appBar)
        val cardTitle = arguments?.getString("title") ?: "Unit Converter"
        val cardType = arguments?.getString("type") ?: "Length"
        toolbar.title = cardTitle
        historyViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[HistoryViewModel::class.java]
        toolbar.setNavigationOnClickListener { parentFragmentManager.popBackStack() }

        // UI containers
        val constraintOne = view.findViewById<ConstraintLayout>(R.id.constraint_one)
        val layoutPercentage = view.findViewById<ConstraintLayout>(R.id.percentage_UI)
        val ratioUI = view.findViewById<ConstraintLayout>(R.id.ratio_UI)
        val bmiUI = view.findViewById<ConstraintLayout>(R.id.bmi_UI)
        switchButton = view.findViewById<ImageView>(R.id.switching_unit_icon)
        value_one_spinner = view.findViewById<Spinner>(R.id.value_one_spinner)
        value_two_spinner = view.findViewById<Spinner>(R.id.value_two_spinner)

        // Hide all
        listOf(constraintOne, layoutPercentage, ratioUI, bmiUI).forEach {
            it.visibility = View.GONE
        }

        // Show relevant layout
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

        // Copy buttons
        copyButtons = listOf(
            view.findViewById(R.id.copy_result_btn),
            view.findViewById(R.id.copy_result_ratio_btn),
            view.findViewById(R.id.copy_result_bmi_btn)
        )
        copyButtons.forEach { setCopyButtonEnabled(it, false) }

        // Insets
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }
        switchButton()
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

        setupEditText(valueOne)
        setupEditText(valueTwo)

        valueOne.requestFocus()
        activeEditText = valueOne

        valueOne.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isUpdating) convertFromFirst()
                updateEqualButtonState(hasValue(valueOne))
            }
        })

//            valueTwo.addTextChangedListener(object : TextWatcher {
//                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//                override fun afterTextChanged(s: Editable?) {}
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    if (!isUpdating) convertFromSecond()
//                    updateEqualButtonState(hasValue(valueTwo))
//                }
//            })


        spinnerOne.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (!isUpdating) convertFromFirst()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerTwo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (!isUpdating) convertFromFirst()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupPercentageUI(view: View) {
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.nunito_semi_bold)
        val tabLayout = view.findViewById<TabLayout>(R.id.percentage_tab_layout)
        val viewPager = view.findViewById<ViewPager2>(R.id.percentage_view_pager)

        viewPager.adapter = PercentagePagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
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

    private fun setupRatioUI(view: View) {
        val xValue = view.findViewById<EditText>(R.id.x_value)
        val yValue = view.findViewById<EditText>(R.id.y_value)
        val resultValue = view.findViewById<EditText>(R.id.result_value)
        copyButton = view.findViewById(R.id.copy_result_ratio_btn)

        setupEditText(xValue)
        setupEditText(yValue)

        resultValue.isFocusable = false
        resultValue.isClickable = false

        setCopyButtonEnabled(false)

        xValue.requestFocus()
        activeEditText = xValue

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val xText = xValue.text.toString().toDoubleOrNull()
                val yText = yValue.text.toString().toDoubleOrNull()

                val enableEqual = xText != null && xText > 0 && yText != null && yText > 0
                updateEqualButtonState(enableEqual)
                calculateRatio(xValue, yValue, resultValue)
            }
        }

        xValue.addTextChangedListener(watcher)
        yValue.addTextChangedListener(watcher)

        copyButton.setOnClickListener {
        }
    }

    private fun calculateRatio(xEt: EditText, yEt: EditText, resultEt: EditText) {
        val x = xEt.text.toString().toDoubleOrNull()
        val y = yEt.text.toString().toDoubleOrNull()

        if (x != null && y != null && x > 0 && y > 0) {
            fun gcd(a: Int, b: Int): Int {
                return if (b == 0) a else gcd(b, a % b)
            }

            val xInt = (x * 1000).toInt()
            val yInt = (y * 1000).toInt()
            val divisor = gcd(xInt, yInt)

            val reducedX = xInt / divisor
            val reducedY = yInt / divisor

            resultEt.setText("$reducedX:$reducedY")
            setCopyButtonEnabled(true)
        } else {
            resultEt.setText("")
            setCopyButtonEnabled(false)
        }
    }


    private fun setCopyButtonEnabled(enabled: Boolean) {
        copyButton.isEnabled = enabled
        if (enabled) {
            copyButton.setBackgroundTintList(
                ContextCompat.getColorStateList(
                    requireContext(),
                    R.color.blue_color
                )
            )
            copyButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        } else {
            copyButton.setBackgroundTintList(
                ContextCompat.getColorStateList(
                    requireContext(),
                    R.color.light_grey
                )
            )
            copyButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
        }
    }

    private fun setupBMIUI(view: View) {
        val feetEt = view.findViewById<EditText>(R.id.feet_value)
        val inchesEt = view.findViewById<EditText>(R.id.inches_value)
        val weightEt = view.findViewById<EditText>(R.id.weight_value)

        listOf(feetEt, inchesEt, weightEt).forEach { setupEditText(it) }

        feetEt.requestFocus()
        activeEditText = feetEt
    }

    private fun setupEditText(edit: EditText) {
        edit.showSoftInputOnFocus = false

        edit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateEqualButtonState(hasValue(edit))
            }
        })

        edit.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                activeEditText = edit
                showKeyboard(true)
                updateEqualButtonState(hasValue(edit))
            }
        }
    }

    private fun hasValue(edit: EditText?): Boolean {
        return edit?.text.toString().toDoubleOrNull()?.let { it > 0 } ?: false
    }


    private fun setupKeyboard(view: View) {
        keyboard = view.findViewById(R.id.keyoboard_grid_layout)
        keyboard.removeAllViews()

        val keys = listOf("7", "8", "9", "4", "5", "6", "1", "2", "3", ".", "0", "⌫")
        keyboard.rowCount = 5
        keyboard.columnCount = 3

        keys.forEachIndexed { index, label ->
            val button = MaterialButton(requireContext()).apply {
                text = label
                textSize = 28f
                setBackgroundResource(R.drawable.keyboard_key_ripple)
                setTextColor(resources.getColor(R.color.text_grey))
                backgroundTintList = null
                typeface = ResourcesCompat.getFont(requireContext(), R.font.inter_regular)
            }

            val param = GridLayout.LayoutParams(
                GridLayout.spec(index / 3, 1f),
                GridLayout.spec(index % 3, 1f)
            ).apply { width = 0; height = 0; setMargins(4, 4, 4, 4) }

            button.layoutParams = param
            button.setOnClickListener { handleKeyboardInput(label) }
            keyboard.addView(button)
        }

        equalButton = MaterialButton(requireContext()).apply {
            text = "="
            textSize = 28f
            setTextColor(resources.getColor(R.color.text_grey))
            setBackgroundResource(R.drawable.equal_button_stroke)
            backgroundTintList = null
            typeface = ResourcesCompat.getFont(requireContext(), R.font.inter_regular)
            setOnClickListener {
                handleEqualClick()
                val given = valueOne.text.toString()
                val received = valueTwo.text.toString()
                val givenUnit = value_one_spinner.selectedItem.toString()
                val receivedUnit = value_two_spinner.selectedItem.toString()
                val unitName = arguments?.getString("type") ?: "Length"
                if (given.isNotEmpty() && received.isNotEmpty()) {
                    val historyItem = HistoryItem(
                        valueGiven = given,
                        valueGivenUnit = givenUnit,
                        valueReceived = received,
                        valueReceivedUnit = receivedUnit,
                        unitName = unitName
                    )
                    historyViewModel.addHistory(historyItem)
                }
            }
        }

        val equalParams = GridLayout.LayoutParams(
            GridLayout.spec(4, 1f),
            GridLayout.spec(0, 3, 1f)
        ).apply { width = 0; height = 0 }

        equalButton.layoutParams = equalParams
        keyboard.addView(equalButton)

        // Initially hide
        keyboard.post { keyboard.translationY = keyboard.height.toFloat() }
    }

    private fun handleKeyboardInput(label: String) {
        activeEditText?.let { edit ->
            when (label) {
                "⌫" -> {
                    val start = edit.selectionStart
                    val end = edit.selectionEnd
                    if (start > 0) {
                        edit.text.replace(start - 1, end, "")
                        edit.setSelection(start - 1)
                    }
                }

                else -> {
                    if (edit.text.toString() == "0") {
                        edit.setText(label)
                        edit.setSelection(1)
                    } else {
                        val start = edit.selectionStart
                        edit.text.insert(start, label)
                        edit.setSelection(start + label.length)
                    }
                }
            }
        }
        updateEqualButtonState(hasValue(activeEditText))
    }

    private fun handleEqualClick() {
        showKeyboard(false)
        copyButtons.forEach { setCopyButtonEnabled(it, hasValue(activeEditText)) }
        updateEqualButtonState(false)
    }

    internal fun updateEqualButtonState(enabled: Boolean) {
        equalButton.isEnabled = enabled
        if (enabled) {
            equalButton.setBackgroundResource(R.drawable.equal_button_filled)
            equalButton.setTextColor(resources.getColor(R.color.white))
        } else {
            equalButton.setBackgroundResource(R.drawable.equal_button_stroke)
            equalButton.setTextColor(resources.getColor(R.color.text_grey))
        }
    }

    private fun setCopyButtonEnabled(button: Button?, enabled: Boolean) {
        button?.isEnabled = enabled
        if (enabled) {
            button?.setBackgroundColor(resources.getColor(R.color.blue_color))
            button?.setTextColor(resources.getColor(R.color.white))
        } else {
            button?.setBackgroundColor(resources.getColor(R.color.light_grey))
            button?.setTextColor(resources.getColor(R.color.text_grey))
        }
    }

    internal fun showKeyboard(show: Boolean) {
        if (!::keyboard.isInitialized) return

        keyboard.post {
            val translationY = if (show) 0f else keyboard.height.toFloat()
            keyboard.animate().translationY(translationY).setDuration(300).start()
        }

        if (!show) activeEditText?.clearFocus()
    }

    private fun switchButton() {
        switchButton.setOnClickListener {
            val tempValue = valueOne.text.toString()
            valueOne.setText(valueTwo.text.toString())
            valueTwo.setText(tempValue)

            val tempPosition = value_one_spinner.selectedItemPosition
            value_one_spinner.setSelection(value_two_spinner.selectedItemPosition)
            value_two_spinner.setSelection(tempPosition)


        }

    }

    private fun convertFromFirst() {
        val input = valueOne.text.toString().toDoubleOrNull() ?: 0.0
        val fromUnit = value_one_spinner.selectedItem.toString()
        val toUnit = value_two_spinner.selectedItem.toString()
        val type = arguments?.getString("type") ?: "Length"

        isUpdating = true
        val result = UnitConverter.convert(input, fromUnit, toUnit, type)
        valueTwo.setText(if (result % 1.0 == 0.0) result.toInt().toString() else result.toString())
        isUpdating = false
    }

}
