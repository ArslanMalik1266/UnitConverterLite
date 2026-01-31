package com.example.unitconverterlite.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.unitconverterlite.R

class PercentageSubtractFragment : Fragment() {

    private var parentConverterFragment: UnitConverterFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentConverterFragment = parentFragment as? UnitConverterFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_percentage_subtract, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val valueOne = view.findViewById<EditText>(R.id.value_one)   // percent
        val valueTwo = view.findViewById<EditText>(R.id.value_two)   // base
        val resultValue = view.findViewById<EditText>(R.id.result_value)

        valueOne.showSoftInputOnFocus = false
        valueTwo.showSoftInputOnFocus = false

        resultValue.isFocusable = false
        resultValue.isClickable = false

        valueOne.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                parentConverterFragment?.activeEditText = valueOne
                parentConverterFragment?.showKeyboard(true)
            }
        }

        valueTwo.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                parentConverterFragment?.activeEditText = valueTwo
                parentConverterFragment?.showKeyboard(true)
            }
        }

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calculatePercentageSubtract(view)
            }
        }

        valueOne.addTextChangedListener(watcher)
        valueTwo.addTextChangedListener(watcher)

        valueOne.requestFocus()
        parentConverterFragment?.activeEditText = valueOne
        parentConverterFragment?.showKeyboard(true)
    }

    private fun calculatePercentageSubtract(view: View) {
        val percentEt = view.findViewById<EditText>(R.id.value_one)
        val baseEt = view.findViewById<EditText>(R.id.value_two)
        val resultEt = view.findViewById<EditText>(R.id.result_value)

        val percent = percentEt.text.toString().toDoubleOrNull() ?: return
        val base = baseEt.text.toString().toDoubleOrNull() ?: return

        val result = base - (base * percent / 100)

        resultEt.setText(
            if (result % 1.0 == 0.0)
                result.toInt().toString()
            else
                result.toString()
        )
    }
}
