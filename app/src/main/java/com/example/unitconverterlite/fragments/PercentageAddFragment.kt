package com.example.unitconverterlite.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.unitconverterlite.R


class PercentageAddFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_percentage_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val parentFragment = parentFragment as? UnitConverterFragment
        val value_one = view.findViewById<EditText>(R.id.value_one)
        val value_two = view.findViewById<EditText>(R.id.value_two)
        val result_value = view.findViewById<EditText>(R.id.result_value)
        value_one.showSoftInputOnFocus = false
        value_two.showSoftInputOnFocus = false
        result_value.isFocusable = false
        result_value.isClickable = false

        value_one.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                parentFragment?.activeEditText = value_one
                parentFragment?.showKeyboard(true)
            }
        }
        value_two.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                parentFragment?.activeEditText = value_two
                parentFragment?.showKeyboard(true)
            }
        }
    }


}