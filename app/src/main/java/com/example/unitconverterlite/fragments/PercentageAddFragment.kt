package com.example.unitconverterlite.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.unitconverterlite.R
import com.example.unitconverterlite.utils.AppPreferences
import com.example.unitconverterlite.utils.SimpleWatcher
import com.example.unitconverterlite.viewModel.PercentageViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PercentageAddFragment : Fragment() {

    private lateinit var valueOne: EditText       // percent
    private lateinit var valueTwo: EditText       // base
    private lateinit var resultValue: EditText
    private lateinit var buttonCopy: Button

    private val viewModel: PercentageViewModel by activityViewModels()
    private var parentConverterFragment: UnitConverterFragment? = null
    private var decimalPrecision: Int = 2

    // Local variables for fragment-specific input
    private var localPercent: String = ""
    private var localBase: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentConverterFragment = parentFragment as? UnitConverterFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_percentage_add, container, false)
    }

    override fun onResume() {
        super.onResume()
        // Request focus for valueOne
        valueOne.requestFocus()
        parentConverterFragment?.activeEditText = valueOne
        parentConverterFragment?.showKeyboard(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        valueOne = view.findViewById(R.id.value_one)
        valueTwo = view.findViewById(R.id.value_two)
        resultValue = view.findViewById(R.id.result_value)
        buttonCopy = view.findViewById(R.id.copy_result_btn)

        valueOne.showSoftInputOnFocus = false
        valueTwo.showSoftInputOnFocus = false

        resultValue.isFocusable = false
        resultValue.isClickable = false

        setCopyButtonEnabled(false)

        setupFocusHandling()
        setupTextWatchers()


        val appPrefs = AppPreferences(requireContext())
        lifecycleScope.launch {
            decimalPrecision = appPrefs.decimalPrecisionFlow.first()
            viewModel.setDecimalPrecision(decimalPrecision)
        }
    }

    private fun setupFocusHandling() {
        valueOne.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) parentConverterFragment?.activeEditText = valueOne
        }

        valueTwo.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) parentConverterFragment?.activeEditText = valueTwo
        }
    }

    private fun setupTextWatchers() {
        valueOne.addTextChangedListener(SimpleWatcher {
            localPercent = valueOne.text.toString()
            updateResult()
        })

        valueTwo.addTextChangedListener(SimpleWatcher {
            localBase = valueTwo.text.toString()
            updateResult()
        })
    }

    private fun updateResult() {
        val result = viewModel.calculateAdd(localPercent, localBase)
        if (result != null) {
            resultValue.setText(viewModel.formatDecimal(result))
            setCopyButtonEnabled(true)
        } else {
            resultValue.setText("")
            setCopyButtonEnabled(false)
        }
    }

    private fun setCopyButtonEnabled(enabled: Boolean) {
        buttonCopy.isEnabled = enabled
        if (enabled) {
            buttonCopy.setBackgroundTintList(
                resources.getColorStateList(R.color.app_color)
            )
            buttonCopy.setTextColor(resources.getColor(R.color.white))
        } else {
            buttonCopy.setBackgroundTintList(
                resources.getColorStateList(R.color.stroke)
            )
            buttonCopy.setTextColor(resources.getColor(R.color.light_gray))
        }
    }
}
