package com.example.unitconverterlite.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.unitconverterlite.R
import com.example.unitconverterlite.utils.SimpleWatcher
import com.example.unitconverterlite.viewModel.PercentageViewModel

class PercentageSubtractFragment : Fragment() {

    private lateinit var valueOne: EditText       // percent
    private lateinit var valueTwo: EditText       // base
    private lateinit var resultValue: EditText
    private lateinit var buttonCopy: Button

    private val viewModel: PercentageViewModel by activityViewModels()
    private var parentConverterFragment: UnitConverterFragment? = null

    // Local variables to store input so other fragments don't interfere
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
        return inflater.inflate(R.layout.fragment_percentage_subtract, container, false)
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
        val result = viewModel.calculateSubtract(localPercent, localBase)
        if (result != null) {
            resultValue.setText(
                if (result % 1.0 == 0.0) result.toInt().toString() else viewModel.formatDecimal(result)
            )
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
                ContextCompat.getColorStateList(requireContext(), R.color.app_color)
            )
            buttonCopy.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        } else {
            buttonCopy.setBackgroundTintList(
                ContextCompat.getColorStateList(requireContext(), R.color.stroke)
            )
            buttonCopy.setTextColor(ContextCompat.getColor(requireContext(), R.color.light_gray))
        }
    }
}
