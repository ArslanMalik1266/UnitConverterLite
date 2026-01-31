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

class PercentageOfFragment : Fragment() {

    private var parentConverterFragment: UnitConverterFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentConverterFragment = parentFragment as? UnitConverterFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_percentage_of, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val valueOne = view.findViewById<EditText>(R.id.what_is_value)      // percent
        val valueTwo = view.findViewById<EditText>(R.id.percent_of_value)  // base
        val resultValue = view.findViewById<EditText>(R.id.result_value)

        // Disable system keyboard
        valueOne.showSoftInputOnFocus = false
        valueTwo.showSoftInputOnFocus = false

        // Result is read-only
        resultValue.isFocusable = false
        resultValue.isClickable = false

        // Focus handling (custom keyboard)
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

        // TextWatcher for live calculation
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calculatePercentageOf(view)
            }
        }

        valueOne.addTextChangedListener(watcher)
        valueTwo.addTextChangedListener(watcher)

        valueOne.requestFocus()
        parentConverterFragment?.activeEditText = valueOne
        parentConverterFragment?.showKeyboard(true)
    }

    private fun calculatePercentageOf(view: View) {
        val percentEt = view.findViewById<EditText>(R.id.what_is_value)
        val baseEt = view.findViewById<EditText>(R.id.percent_of_value)
        val resultEt = view.findViewById<EditText>(R.id.result_value)

        val percent = percentEt.text.toString().toDoubleOrNull() ?: return
        val base = baseEt.text.toString().toDoubleOrNull() ?: return

        val result = (percent * base) / 100

        resultEt.setText(
            if (result % 1.0 == 0.0)
                result.toInt().toString()
            else
                result.toString()
        )
    }
}
