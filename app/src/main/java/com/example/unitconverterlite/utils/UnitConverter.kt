package com.example.unitconverterlite.utils

object UnitConverter {

    fun convert(value: Double, fromUnit: String, toUnit: String, type: String): Double {
        return when (type) {
            "Length" -> convertLength(value, fromUnit, toUnit)
            "Area" -> convertArea(value, fromUnit, toUnit)
            "Time" -> convertTime(value, fromUnit, toUnit)
            "Volume" -> convertVolume(value, fromUnit, toUnit)
            "Temperature" -> convertTemperature(value, fromUnit, toUnit)
            "Weight" -> convertWeight(value, fromUnit, toUnit)
            "Speed" -> convertSpeed(value, fromUnit, toUnit)
            "Energy" -> convertEnergy(value, fromUnit, toUnit)
            "Power" -> convertPower(value, fromUnit, toUnit)
            "Torque" -> convertTorque(value, fromUnit, toUnit)
            "Pressure" -> convertPressure(value, fromUnit, toUnit)
            "Force" -> convertForce(value, fromUnit, toUnit)
            "Angle" -> convertAngle(value, fromUnit, toUnit)
            "Currency" -> convertCurrency(value, fromUnit, toUnit)
            "Sound" -> convertSound(value, fromUnit, toUnit)
            "BMI" -> convertBMI(value, fromUnit, toUnit)
            else -> value
        }
    }

    // ------------------ Length ------------------
    private fun convertLength(value: Double, fromUnit: String, toUnit: String): Double {
        val meters = when (fromUnit) {
            "Meters" -> value
            "Kilometers" -> value * 1000
            "Miles" -> value * 1609.34
            "Feet" -> value * 0.3048
            "Inches" -> value * 0.0254
            else -> value
        }
        return when (toUnit) {
            "Meters" -> meters
            "Kilometers" -> meters / 1000
            "Miles" -> meters / 1609.34
            "Feet" -> meters / 0.3048
            "Inches" -> meters / 0.0254
            else -> meters
        }
    }

    // ------------------ Area ------------------
    private fun convertArea(value: Double, fromUnit: String, toUnit: String): Double {
        val sqm = when (fromUnit) {
            "Square Meter" -> value
            "Square Kilometer" -> value * 1_000_000
            "Square Mile" -> value * 2_589_988.11
            "Hectare" -> value * 10_000
            "Acre" -> value * 4046.86
            else -> value
        }
        return when (toUnit) {
            "Square Meter" -> sqm
            "Square Kilometer" -> sqm / 1_000_000
            "Square Mile" -> sqm / 2_589_988.11
            "Hectare" -> sqm / 10_000
            "Acre" -> sqm / 4046.86
            else -> sqm
        }
    }

    // ------------------ Time ------------------
    private fun convertTime(value: Double, fromUnit: String, toUnit: String): Double {
        val seconds = when (fromUnit) {
            "Seconds" -> value
            "Minutes" -> value * 60
            "Hours" -> value * 3600
            "Days" -> value * 86400
            "Weeks" -> value * 604800
            else -> value
        }
        return when (toUnit) {
            "Seconds" -> seconds
            "Minutes" -> seconds / 60
            "Hours" -> seconds / 3600
            "Days" -> seconds / 86400
            "Weeks" -> seconds / 604800
            else -> seconds
        }
    }

    // ------------------ Volume ------------------
    private fun convertVolume(value: Double, fromUnit: String, toUnit: String): Double {
        val liters = when (fromUnit) {
            "Liter" -> value
            "Milliliter" -> value / 1000
            "Gallon" -> value * 3.78541
            "Cubic Meter" -> value * 1000
            "Cubic Feet" -> value * 28.3168
            else -> value
        }
        return when (toUnit) {
            "Liter" -> liters
            "Milliliter" -> liters * 1000
            "Gallon" -> liters / 3.78541
            "Cubic Meter" -> liters / 1000
            "Cubic Feet" -> liters / 28.3168
            else -> liters
        }
    }

    // ------------------ Temperature ------------------
    private fun convertTemperature(value: Double, fromUnit: String, toUnit: String): Double {
        val celsius = when (fromUnit) {
            "Celsius" -> value
            "Fahrenheit" -> (value - 32) * 5/9
            "Kelvin" -> value - 273.15
            else -> value
        }
        return when (toUnit) {
            "Celsius" -> celsius
            "Fahrenheit" -> celsius * 9/5 + 32
            "Kelvin" -> celsius + 273.15
            else -> celsius
        }
    }

    // ------------------ Weight ------------------
    private fun convertWeight(value: Double, fromUnit: String, toUnit: String): Double {
        val kg = when (fromUnit) {
            "Kilograms" -> value
            "Grams" -> value / 1000
            "Pounds" -> value * 0.453592
            "Ounces" -> value * 0.0283495
            else -> value
        }
        return when (toUnit) {
            "Kilograms" -> kg
            "Grams" -> kg * 1000
            "Pounds" -> kg / 0.453592
            "Ounces" -> kg / 0.0283495
            else -> kg
        }
    }

    // ------------------ Speed ------------------
    private fun convertSpeed(value: Double, fromUnit: String, toUnit: String): Double {
        val mps = when (fromUnit) {
            "m/s" -> value
            "km/h" -> value / 3.6
            "mph" -> value * 0.44704
            "ft/s" -> value * 0.3048
            else -> value
        }
        return when (toUnit) {
            "m/s" -> mps
            "km/h" -> mps * 3.6
            "mph" -> mps / 0.44704
            "ft/s" -> mps / 0.3048
            else -> mps
        }
    }

    // ------------------ Energy ------------------
    private fun convertEnergy(value: Double, fromUnit: String, toUnit: String): Double {
        val joules = when (fromUnit) {
            "Joule" -> value
            "Calorie" -> value * 4.184
            "kWh" -> value * 3_600_000
            "BTU" -> value * 1055.06
            else -> value
        }
        return when (toUnit) {
            "Joule" -> joules
            "Calorie" -> joules / 4.184
            "kWh" -> joules / 3_600_000
            "BTU" -> joules / 1055.06
            else -> joules
        }
    }

    // ------------------ Power ------------------
    private fun convertPower(value: Double, fromUnit: String, toUnit: String): Double {
        val watt = when (fromUnit) {
            "Watt" -> value
            "Kilowatt" -> value * 1000
            "Horsepower" -> value * 745.7
            else -> value
        }
        return when (toUnit) {
            "Watt" -> watt
            "Kilowatt" -> watt / 1000
            "Horsepower" -> watt / 745.7
            else -> watt
        }
    }

    // ------------------ Torque ------------------
    private fun convertTorque(value: Double, fromUnit: String, toUnit: String): Double {
        val nm = when (fromUnit) {
            "Nm" -> value
            "kg·m" -> value * 9.80665
            "lb·ft" -> value * 1.35582
            else -> value
        }
        return when (toUnit) {
            "Nm" -> nm
            "kg·m" -> nm / 9.80665
            "lb·ft" -> nm / 1.35582
            else -> nm
        }
    }

    // ------------------ Pressure ------------------
    private fun convertPressure(value: Double, fromUnit: String, toUnit: String): Double {
        val pa = when (fromUnit) {
            "Pa" -> value
            "kPa" -> value * 1000
            "Bar" -> value * 100000
            "psi" -> value * 6894.76
            else -> value
        }
        return when (toUnit) {
            "Pa" -> pa
            "kPa" -> pa / 1000
            "Bar" -> pa / 100000
            "psi" -> pa / 6894.76
            else -> pa
        }
    }

    // ------------------ Force ------------------
    private fun convertForce(value: Double, fromUnit: String, toUnit: String): Double {
        val newton = when (fromUnit) {
            "Newton" -> value
            "Kilogram-force" -> value * 9.80665
            "Pound-force" -> value * 4.44822
            else -> value
        }
        return when (toUnit) {
            "Newton" -> newton
            "Kilogram-force" -> newton / 9.80665
            "Pound-force" -> newton / 4.44822
            else -> newton
        }
    }

    // ------------------ Angle ------------------
    private fun convertAngle(value: Double, fromUnit: String, toUnit: String): Double {
        val degree = when (fromUnit) {
            "Degree" -> value
            "Radian" -> value * 180 / Math.PI
            "Gradian" -> value * 0.9
            else -> value
        }
        return when (toUnit) {
            "Degree" -> degree
            "Radian" -> degree * Math.PI / 180
            "Gradian" -> degree / 0.9
            else -> degree
        }
    }

    // ------------------ Currency ------------------
    private fun convertCurrency(value: Double, fromUnit: String, toUnit: String): Double {
        val rates = mapOf(
            "USD" to 1.0,
            "EUR" to 0.93,
            "GBP" to 0.81,
            "JPY" to 136.0,
            "PKR" to 280.0
        )
        val usdValue = value / (rates[fromUnit] ?: 1.0)
        return usdValue * (rates[toUnit] ?: 1.0)
    }

    // ------------------ Sound ------------------
    private fun convertSound(value: Double, fromUnit: String, toUnit: String): Double {
        return value // only decibel, no conversion needed
    }

    // ------------------ BMI ------------------
    private fun convertBMI(value: Double, fromUnit: String, toUnit: String): Double {
        return value // BMI is usually calculated, not converted
    }

}
