package com.example.unitconverterlite.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unitconverterlite.utils.UnitConverter

class UnitConverterViewModel : ViewModel() {

    private val _valueOne = MutableLiveData<String>("")
    val valueOne: LiveData<String> = _valueOne

    private val _valueTwo = MutableLiveData<String>("")
    val valueTwo: LiveData<String> = _valueTwo

    private val _equalEnabled = MutableLiveData(false)
    val equalEnabled: LiveData<Boolean> = _equalEnabled

    fun onValueOneChanged(
        value: String,
        fromUnit: String,
        toUnit: String,
        type: String,
        decimalPrecision: Int = 2,
        context: Context
    ) {
        _valueOne.value = value
        convert(value, fromUnit, toUnit, type, decimalPrecision, isFromOne = true, context)
    }

    fun resetValues() {
        _valueOne.value = "0"
        _valueTwo.value = "0"
    }

    private fun convert(
        value: String,
        fromUnit: String,
        toUnit: String,
        type: String,
        decimalPrecision: Int,
        isFromOne: Boolean,
        context: Context
    ) {
        val input = value.toDoubleOrNull() ?: 0.0
        if (input == 0.0) {
            _equalEnabled.value = false
            if (isFromOne) _valueTwo.value = "" else _valueOne.value = ""
            return
        }

        val result = if (isFromOne)
            UnitConverter.convert(context,input, fromUnit, toUnit, type)
        else
            UnitConverter.convert(context,input, toUnit, fromUnit, type)

        val formattedResult = when (decimalPrecision) {
            0 -> result.toInt().toString()
            2 -> String.format("%.2f", result)
            4 -> String.format("%.4f", result)
            else -> result.toString()
        }

        if (isFromOne) _valueTwo.value = formattedResult
        else _valueOne.value = formattedResult

        _equalEnabled.value = true
    }
}
