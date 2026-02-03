package com.example.unitconverterlite.viewModel

import androidx.lifecycle.ViewModel
import java.math.RoundingMode
import java.text.DecimalFormat

class PercentageViewModel : ViewModel() {

    private var decimalPrecision: Int = 2 // default precision

    fun setDecimalPrecision(precision: Int?) {
        precision?.let {
            decimalPrecision = it
        }
    }

    fun calculateAdd(percentStr: String, baseStr: String): Double? {
        val p = percentStr.toDoubleOrNull()
        val b = baseStr.toDoubleOrNull()
        return if (p != null && b != null) b + (b * p / 100) else null
    }

    fun calculateSubtract(percentStr: String, baseStr: String): Double? {
        val p = percentStr.toDoubleOrNull()
        val b = baseStr.toDoubleOrNull()
        return if (p != null && b != null) b - (b * p / 100) else null
    }

    fun calculateOf(percentStr: String, baseStr: String): Double? {
        val p = percentStr.toDoubleOrNull()
        val b = baseStr.toDoubleOrNull()
        return if (p != null && b != null) (p * b) / 100 else null
    }

    fun formatDecimal(value: Double): String {
        val pattern = buildString {
            append("0")
            if (decimalPrecision > 0) {
                append(".")
                repeat(decimalPrecision) { append("0") }
            }
        }
        val df = DecimalFormat(pattern)
        df.roundingMode = RoundingMode.HALF_UP
        return df.format(value)
    }
}
