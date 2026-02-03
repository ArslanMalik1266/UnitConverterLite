package com.example.unitconverterlite.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.GridLayout.LayoutParams
import androidx.appcompat.app.ActionBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.unitconverterlite.Adaptor.PercentagePagerAdapter
import com.example.unitconverterlite.DataClass.HistoryItem
import com.example.unitconverterlite.MainActivity
import com.example.unitconverterlite.R
import com.example.unitconverterlite.utils.AppPreferences
import com.example.unitconverterlite.utils.SimpleWatcher
import com.example.unitconverterlite.utils.UnitConverter
import com.example.unitconverterlite.viewModel.BMIViewModel
import com.example.unitconverterlite.viewModel.HistoryViewModel
import com.example.unitconverterlite.viewModel.RatioViewModel
import com.example.unitconverterlite.viewModel.UnitConverterViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UnitConverterFragment : Fragment() {

    private lateinit var valueOne: EditText
    private lateinit var valueTwo: EditText
    internal var activeEditText: EditText? = null
    private lateinit var equalButton: MaterialButton
    private lateinit var keyboard: GridLayout
    private lateinit var switchButton: ImageView
    private lateinit var viewpager: ViewPager2
    private lateinit var value_one_spinner: Spinner
    private lateinit var value_two_spinner: Spinner
    internal lateinit var copyButton: MaterialButton
    var rotated = false
    private lateinit var historyViewModel: HistoryViewModel
    private val ratioViewModel: RatioViewModel by activityViewModels()
    private val bmiViewModel: BMIViewModel by activityViewModels()
    private val unitConverterViewModel: UnitConverterViewModel by activityViewModels()


    private val unitsMap = mapOf(
        "Length" to listOf("Meters", "Kilometers", "Miles", "Feet", "Inches"),
        "Area" to listOf("Square Meter", "Square Kilometer", "Square Mile", "Hectare", "Acre"),
        "Time" to listOf("Seconds", "Minutes", "Hours", "Days", "Weeks"),
        "Volume" to listOf("Liter", "Milliliter", "Gallon", "Cubic Meter", "Cubic Feet"),
        "Temperature" to listOf("Celsius", "Fahrenheit", "Kelvin"),
        "Weight" to listOf("Kilograms", "Grams", "Pounds", "Ounces"),
        "Speed" to listOf("meters per second", "kilometers per hour", "miles per hour", "feet per second"),
        "Energy" to listOf("Joule", "Calorie", "kWh", "BTU"),
        "Power" to listOf("Watt", "Kilowatt", "Horsepower"),
        "Torque" to listOf("Newton meter", "Kilogram meter", "Pound-foot"),
        "Pressure" to listOf("Pascal", "Kilopascal", "Bar", "Pounds per square inch"),
        "Force" to listOf("Newton", "Kilogram-force", "Pound-force"),
        "Angle" to listOf("Degree", "Radian", "Gradian"),
        "Currency" to listOf("USD", "EUR", "GBP", "JPY", "PKR"),
        "Sound" to listOf("Hertz","Kilohertz", "Megahertz", "Pascal", "Micropascal", "Decibel", "Watt per square meter"),
        "BMI" to listOf("Metric", "Imperial")
    )

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.showBottomNav(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unitConverterViewModel.resetValues()
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
        copyButton = view.findViewById<MaterialButton>(R.id.copy_result_btn)

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
        valueOne = view.findViewById<EditText>(R.id.value_one)
        valueTwo = view.findViewById<EditText>(R.id.value_two)
        viewpager = view.findViewById<ViewPager2>(R.id.percentage_view_pager)



        setCopyButtonEnabled(false)


        // Hide all
        listOf(constraintOne, layoutPercentage, ratioUI, bmiUI).forEach {
            it.visibility = View.GONE
        }

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


        // Insets
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }
        switchButton()

        copyButton.setOnClickListener {
            val resultText = when {
                ratioUI.visibility == View.VISIBLE -> view.findViewById<EditText>(R.id.result_value).text.toString()
                bmiUI.visibility == View.VISIBLE -> view.findViewById<EditText>(R.id.result_bmi_value).text.toString()
                else -> valueTwo.text.toString()
            }

            if (resultText.isNotEmpty()) {
                val clipboard =
                    requireContext().getSystemService(android.content.ClipboardManager::class.java)
                val clip = android.content.ClipData.newPlainText("result", resultText)
                clipboard.setPrimaryClip(clip)
            }
        }
    }


    private fun setupRegularUI(view: View, cardType: String) {
        val spinnerOne = view.findViewById<Spinner>(R.id.value_one_spinner)
        val spinnerTwo = view.findViewById<Spinner>(R.id.value_two_spinner)

        val units = unitsMap[cardType] ?: listOf("Unit1", "Unit2")
        val adapter1 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, units)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val adapter2 = ArrayAdapter(requireContext(), R.layout.spinner_dropdown_item, units)
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerOne.adapter = adapter1
        spinnerTwo.adapter = adapter2
        spinnerOne.setSelection(0)
        spinnerTwo.setSelection(1)
        spinnerOne.post {
            spinnerOne.dropDownWidth = spinnerOne.width
        }

        spinnerTwo.post {
            spinnerTwo.dropDownWidth = spinnerTwo.width
        }

        valueOne = view.findViewById(R.id.value_one)
        valueTwo = view.findViewById(R.id.value_two)

        setupEditText(valueOne)
        setupEditText(valueTwo)

        valueOne.requestFocus()
        activeEditText = valueOne

        // Observe LiveData
        unitConverterViewModel.valueOne.observe(viewLifecycleOwner) { value ->
            if (valueOne.text.toString() != value) valueOne.setText(value)
        }

        unitConverterViewModel.valueTwo.observe(viewLifecycleOwner) { value ->
            if (valueTwo.text.toString() != value) valueTwo.setText(value)
        }

        unitConverterViewModel.equalEnabled.observe(viewLifecycleOwner) { enabled ->
            updateEqualButtonState(enabled)
        }

        val appPrefs = AppPreferences(requireContext())
        lifecycleScope.launch {
            val decimalPrecision = appPrefs.decimalPrecisionFlow.first() // get current saved value

            valueOne.addTextChangedListener(SimpleWatcher {
                val fromUnit = spinnerOne.selectedItem.toString()
                val toUnit = spinnerTwo.selectedItem.toString()
                val type = arguments?.getString("type") ?: "Length"
                unitConverterViewModel.onValueOneChanged(
                    valueOne.text.toString(),
                    fromUnit,
                    toUnit,
                    type,
                    decimalPrecision
                )
            })

            spinnerOne.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val fromUnit = spinnerOne.selectedItem.toString()
                    val toUnit = spinnerTwo.selectedItem.toString()
                    val type = arguments?.getString("type") ?: "Length"
                    unitConverterViewModel.onValueOneChanged(
                        valueOne.text.toString(),
                        fromUnit,
                        toUnit,
                        type,
                        decimalPrecision
                    )
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
                    val fromUnit = spinnerOne.selectedItem.toString()
                    val toUnit = spinnerTwo.selectedItem.toString()
                    val type = arguments?.getString("type") ?: "Length"
                    unitConverterViewModel.onValueOneChanged(
                        valueOne.text.toString(),
                        fromUnit,
                        toUnit,
                        type,
                        decimalPrecision
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
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
        copyButton = view.findViewById(R.id.copy_result_btn)

        setupEditText(xValue)
        setupEditText(yValue)

        resultValue.isFocusable = false
        resultValue.isClickable = false
        setCopyButtonEnabled(false)

        xValue.requestFocus()
        activeEditText = xValue

        // Observe ViewModel
        ratioViewModel.result.observe(viewLifecycleOwner) { result ->
            resultValue.setText(result)
            setCopyButtonEnabled(result.isNotEmpty())
        }

        ratioViewModel.equalEnabled.observe(viewLifecycleOwner) { enabled ->
            updateEqualButtonState(enabled)
        }

        xValue.addTextChangedListener(SimpleWatcher {
            ratioViewModel.onXChanged(xValue.text.toString())
        })

        yValue.addTextChangedListener(SimpleWatcher {
            ratioViewModel.onYChanged(yValue.text.toString())
        })

        copyButton.setOnClickListener {
            val text = resultValue.text.toString()
            if (text.isNotEmpty()) {
                val clipboard =
                    requireContext().getSystemService(android.content.ClipboardManager::class.java)
                val clip = android.content.ClipData.newPlainText("ratio", text)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(requireContext(), "Copied: $text", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setCopyButtonEnabled(enabled: Boolean) {
        copyButton.isEnabled = enabled
        if (enabled) {
            copyButton.setBackgroundTintList(
                ContextCompat.getColorStateList(
                    requireContext(),
                    R.color.app_color
                )
            )
            copyButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        } else {
            copyButton.setBackgroundTintList(
                ContextCompat.getColorStateList(
                    requireContext(),
                    R.color.stroke
                )
            )
            copyButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.light_gray))
        }
    }


    private fun setupBMIUI(view: View) {
        val feetEt = view.findViewById<EditText>(R.id.feet_value)
        val inchesEt = view.findViewById<EditText>(R.id.inches_value)
        val weightEt = view.findViewById<EditText>(R.id.weight_value)
        val resultEt = view.findViewById<EditText>(R.id.result_bmi_value)
        copyButton = view.findViewById(R.id.copy_result_btn)

        listOf(feetEt, inchesEt, weightEt).forEach { setupEditText(it) }

        resultEt.isFocusable = false
        resultEt.isClickable = false
        setCopyButtonEnabled(false)

        feetEt.requestFocus()
        activeEditText = feetEt

        bmiViewModel.bmiResult.observe(viewLifecycleOwner) { bmi ->
            resultEt.setText(bmi)
            setCopyButtonEnabled(bmi.isNotEmpty())
        }

        bmiViewModel.equalEnabled.observe(viewLifecycleOwner) { enabled ->
            updateEqualButtonState(enabled)
        }

        feetEt.addTextChangedListener(SimpleWatcher {
            bmiViewModel.onFeetChanged(feetEt.text.toString())
        })

        inchesEt.addTextChangedListener(SimpleWatcher {
            bmiViewModel.onInchesChanged(inchesEt.text.toString())
        })

        weightEt.addTextChangedListener(SimpleWatcher {
            bmiViewModel.onWeightChanged(weightEt.text.toString())
        })

        copyButton.setOnClickListener {
            val text = resultEt.text.toString()
            if (text.isNotEmpty()) {
                val clipboard =
                    requireContext().getSystemService(android.content.ClipboardManager::class.java)
                val clip = android.content.ClipData.newPlainText("BMI", text)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(requireContext(), "Copied: $text", Toast.LENGTH_SHORT).show()
            }
        }
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
                setTextColor(resources.getColor(R.color.light_gray))
                backgroundTintList = null
                typeface = ResourcesCompat.getFont(requireContext(), R.font.inter_regular)
            }

            val param = GridLayout.LayoutParams(
                GridLayout.spec(index / 3, 1f),
                GridLayout.spec(index % 3, 1f)
            ).apply { width = 0; height = 0; setMargins(8, 4, 8, 4) }

            button.layoutParams = param
            button.setOnClickListener { handleKeyboardInput(label) }
            keyboard.addView(button)
        }

        equalButton = MaterialButton(requireContext()).apply {
            text = "="
            textSize = 28f
            setTextColor(resources.getColor(R.color.light_gray))
            setBackgroundResource(R.drawable.equal_button_stroke)
            backgroundTintList = null
            typeface = ResourcesCompat.getFont(requireContext(), R.font.inter_regular)
            setOnClickListener {
                handleEqualClick()
                lifecycleScope.launch {
                    val autoSaveEnabled = AppPreferences(requireContext()).isAutoSaveEnabled.first()
                    if (autoSaveEnabled) {
                        performConversionAndSaveHistory()
                    } else {
                        // just calculate without saving
                    }
                }
            }
        }

        val equalParams = LayoutParams(
            GridLayout.spec(4),
            GridLayout.spec(0, 3)
        ).apply {
            width = LayoutParams.MATCH_PARENT
            width = LayoutParams.MATCH_PARENT
        }

        equalButton.layoutParams = equalParams
        keyboard.addView(equalButton)

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
        updateEqualButtonState(false)
        setCopyButtonEnabled(copyButton, true)
    }

    internal fun updateEqualButtonState(enabled: Boolean) {
        equalButton.isEnabled = enabled
        if (enabled) {
            equalButton.setBackgroundResource(R.drawable.equal_button_filled)
            equalButton.setTextColor(resources.getColor(R.color.white))
        } else {
            equalButton.setBackgroundResource(R.drawable.equal_button_stroke)
            equalButton.setTextColor(resources.getColor(R.color.light_gray))
        }
    }

    private fun setCopyButtonEnabled(button: Button?, enabled: Boolean) {
        button?.isEnabled = enabled
        if (enabled) {
            button?.visibility = View.VISIBLE
            button?.setBackgroundColor(resources.getColor(R.color.app_color))
            button?.setTextColor(resources.getColor(R.color.white))
        } else {
         button?.visibility = View.GONE

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
            val animator = ObjectAnimator.ofFloat(switchButton, "rotation", 0f, 180f)
            animator.duration = 300
            animator.start()
            val tempValue = valueOne.text.toString()
            val tempValueTwo = valueTwo.text.toString()

            val tempSpinnerPosition = value_one_spinner.selectedItemPosition
            val tempSpinnerPositionTwo = value_two_spinner.selectedItemPosition

            valueOne.setText(tempValueTwo)
            valueTwo.setText(tempValue)

            value_one_spinner.setSelection(tempSpinnerPositionTwo)
            value_two_spinner.setSelection(tempSpinnerPosition)
        }
    }


    internal fun addHistory(
        valueGiven: String,
        valueGivenUnit: String,
        valueReceived: String,
        valueReceivedUnit: String,
        unitName: String
    ) {
        if (valueGiven.isNotEmpty() && valueReceived.isNotEmpty()) {
            val historyItem = HistoryItem(
                valueGiven = valueGiven,
                valueGivenUnit = valueGivenUnit,
                valueReceived = valueReceived,
                valueReceivedUnit = valueReceivedUnit,
                unitName = unitName
            )
            historyViewModel.addHistory(historyItem)
        }


    }

    private fun performConversionAndSaveHistory() {
        val type = arguments?.getString("type") ?: "Length"

        when (type) {
            "BMI" -> {
                val feet = view?.findViewById<EditText>(R.id.feet_value)?.text.toString()
                val inches = view?.findViewById<EditText>(R.id.inches_value)?.text.toString()
                val weight_kg = view?.findViewById<TextView>(R.id.kg_tv)?.text.toString()
                val weight = view?.findViewById<EditText>(R.id.weight_value)?.text.toString()
                val result = view?.findViewById<EditText>(R.id.result_bmi_value)?.text.toString()

                if (feet.isNotEmpty() && inches.isNotEmpty() && weight.isNotEmpty() && result.isNotEmpty()) {
                    addHistory(
                        valueGiven = "$feet'$inches $weight",
                        valueGivenUnit = weight_kg,
                        valueReceived = result,
                        valueReceivedUnit = "",
                        unitName = "BMI"
                    )
                }
            }


            "Ratio" -> {
                val x = view?.findViewById<EditText>(R.id.x_value)?.text.toString()
                val y = view?.findViewById<EditText>(R.id.y_value)?.text.toString()
                val result = view?.findViewById<EditText>(R.id.result_value)?.text.toString()

                if (x.isNotEmpty() && y.isNotEmpty() && result.isNotEmpty()) {
                    addHistory(
                        valueGiven = x,
                        valueGivenUnit = y,
                        valueReceived = result,
                        valueReceivedUnit = "Ratio",
                        unitName = "Ratio"
                    )
                }
            }

            "Percentage" -> {

                val currentFragment = (viewpager.adapter as? PercentagePagerAdapter)
                    ?.getFragmentAt(viewpager.currentItem)

                val valueGiven = when (currentFragment) {
                    is PercentageOfFragment -> currentFragment.getValueGivenForHistory()
                    is PercentageAddFragment -> currentFragment.getValueGivenForHistory()
                    is PercentageSubtractFragment -> currentFragment.getValueGivenForHistory()
                    else -> ""
                }
                val valueReceived = when (currentFragment) {
                    is PercentageOfFragment -> currentFragment.getResultForHistory()
                    is PercentageAddFragment -> currentFragment.getResultForHistory()
                    is PercentageSubtractFragment -> currentFragment.getResultForHistory()
                    else -> ""
                }
                if (valueGiven.isNotEmpty() && valueReceived.isNotEmpty()) {
                    val unitName = when (viewpager.currentItem) {
                        0 -> "Percentage"
                        1 -> "Percentage"
                        2 -> "Percentage"
                        else -> "Percentage"
                    }

                    addHistory(
                        valueGiven = valueGiven,
                        valueGivenUnit = "",
                        valueReceived = valueReceived,
                        valueReceivedUnit = "",
                        unitName = unitName
                    )
                }

            }


            else -> {
                val valueGiven = valueOne.text.toString()
                val valueReceived = valueTwo.text.toString()
                val fromUnit = value_one_spinner.selectedItem?.toString() ?: ""
                val toUnit = value_two_spinner.selectedItem?.toString() ?: ""

                if (valueGiven.isNotEmpty() && valueReceived.isNotEmpty()) {
                    addHistory(
                        valueGiven = valueGiven,
                        valueGivenUnit = fromUnit,
                        valueReceived = valueReceived,
                        valueReceivedUnit = toUnit,
                        unitName = type
                    )
                }
            }
        }
    }

    fun copyResultFromChild(resultText: String) {
        if (resultText.isNotEmpty()) {
            val clipboard =
                requireContext().getSystemService(android.content.ClipboardManager::class.java)
            val clip = android.content.ClipData.newPlainText("result", resultText)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), "Copied: $resultText", Toast.LENGTH_SHORT).show()
        }
    }


}
