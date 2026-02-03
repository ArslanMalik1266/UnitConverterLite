package com.example.unitconverterlite.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RatioViewModel : ViewModel() {

    private val _xValue = MutableLiveData<String>("")
    val xValue: LiveData<String> = _xValue

    private val _yValue = MutableLiveData<String>("")
    val yValue: LiveData<String> = _yValue

    private val _result = MutableLiveData<String>("")
    val result: LiveData<String> = _result

    private val _equalEnabled = MutableLiveData(false)
    val equalEnabled: LiveData<Boolean> = _equalEnabled

    fun onXChanged(value: String) {
        _xValue.value = value
        calculateRatio()
    }

    fun onYChanged(value: String) {
        _yValue.value = value
        calculateRatio()
    }

    private fun calculateRatio() {
        val x = _xValue.value?.toDoubleOrNull()
        val y = _yValue.value?.toDoubleOrNull()

        if (x != null && y != null && x > 0 && y > 0) {
            val gcd = { a: Int, b: Int ->
                tailrec fun gcdRec(a: Int, b: Int): Int = if (b == 0) a else gcdRec(b, a % b)
                gcdRec(a, b)
            }

            val xInt = (x * 1000).toInt()
            val yInt = (y * 1000).toInt()
            val divisor = gcd(xInt, yInt)
            val reducedX = xInt / divisor
            val reducedY = yInt / divisor
            _result.value = "$reducedX:$reducedY"
            _equalEnabled.value = true
        } else {
            _result.value = ""
            _equalEnabled.value = false
        }
    }
}
