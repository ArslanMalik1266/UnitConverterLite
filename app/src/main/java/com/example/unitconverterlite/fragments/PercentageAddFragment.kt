package com.example.unitconverterlite.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.example.unitconverterlite.R

class PercentageAddFragment : Fragment() {
    private lateinit var buttonCopy: Button
    private var parentConverterFragment: UnitConverterFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentConverterFragment = parentFragment as? UnitConverterFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_percentage_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val valueOne = view.findViewById<EditText>(R.id.value_one)   // percent
        val valueTwo = view.findViewById<EditText>(R.id.value_two)   // base
        val resultValue = view.findViewById<EditText>(R.id.result_value)
        buttonCopy = view.findViewById(R.id.copy_result_btn)

        // Disable system keyboard
        valueOne.showSoftInputOnFocus = false
        valueTwo.showSoftInputOnFocus = false

        resultValue.isFocusable = false
        resultValue.isClickable = false

        parentConverterFragment?.updateEqualButtonState(false)
        setCopyButtonEnabled(buttonCopy, false)

        valueOne.requestFocus()
        parentConverterFragment?.activeEditText = valueOne
        parentConverterFragment?.showKeyboard(true)

        valueOne.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) parentConverterFragment?.activeEditText = valueOne
        }

        valueTwo.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) parentConverterFragment?.activeEditText = valueTwo
        }

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calculateAndShowResult()
            }
        }

        valueOne.addTextChangedListener(watcher)
        valueTwo.addTextChangedListener(watcher)
    }

    private fun checkEnableEqual(valueOne: EditText, valueTwo: EditText) {
        val percent = valueOne.text.toString().toDoubleOrNull() ?: 0.0
        val base = valueTwo.text.toString().toDoubleOrNull() ?: 0.0

        val enableEqual = percent > 0 && base > 0
        parentConverterFragment?.updateEqualButtonState(enableEqual)

        setCopyButtonEnabled(buttonCopy, false)
    }

    fun calculateAndShowResult() {
        val view = view ?: return
        val percentEt = view.findViewById<EditText>(R.id.value_one)
        val baseEt = view.findViewById<EditText>(R.id.value_two)
        val resultEt = view.findViewById<EditText>(R.id.result_value)

        val percent = percentEt.text.toString().toDoubleOrNull() ?: return
        val base = baseEt.text.toString().toDoubleOrNull() ?: return

        val result = base + (base * percent / 100)

        resultEt.setText(if (result % 1.0 == 0.0) result.toInt().toString() else result.toString())

        setCopyButtonEnabled(buttonCopy, true)
    }

    private fun setCopyButtonEnabled(button: Button?, enabled: Boolean) {
        button ?: return
        button.isEnabled = enabled
        if (enabled) {
            button.setBackgroundTintList(
                ContextCompat.getColorStateList(requireContext(), R.color.blue_color)
            )
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            button.alpha = 1f
        } else {
            button.setBackgroundTintList(
                ContextCompat.getColorStateList(requireContext(), R.color.light_grey)
            )
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
            button.alpha = 1f
        }
    }
}

