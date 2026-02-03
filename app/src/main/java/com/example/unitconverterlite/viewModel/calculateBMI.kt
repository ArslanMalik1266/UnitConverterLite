package com.example.unitconverterlite.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BMIViewModel : ViewModel() {

    private val _feet = MutableLiveData<String>("")
    val feet: LiveData<String> = _feet

    private val _inches = MutableLiveData<String>("")
    val inches: LiveData<String> = _inches

    private val _weight = MutableLiveData<String>("")
    val weight: LiveData<String> = _weight

    private val _bmiResult = MutableLiveData<String>("")
    val bmiResult: LiveData<String> = _bmiResult

    private val _equalEnabled = MutableLiveData(false)
    val equalEnabled: LiveData<Boolean> = _equalEnabled

    fun onFeetChanged(value: String) {
        _feet.value = value
        calculateBMI()
    }

    fun onInchesChanged(value: String) {
        _inches.value = value
        calculateBMI()
    }

    fun onWeightChanged(value: String) {
        _weight.value = value
        calculateBMI()
    }

    private fun calculateBMI() {
        val f = _feet.value?.toDoubleOrNull() ?: 0.0
        val i = _inches.value?.toDoubleOrNull() ?: 0.0
        val w = _weight.value?.toDoubleOrNull() ?: 0.0

        if (w > 0 && (f > 0 || i > 0)) {
            val heightInMeters = ((f * 12 + i) * 0.0254)
            val bmi = w / (heightInMeters * heightInMeters)
            _bmiResult.value = String.format("%.1f", bmi)
            _equalEnabled.value = true
        } else {
            _bmiResult.value = ""
            _equalEnabled.value = false
        }
    }
}
