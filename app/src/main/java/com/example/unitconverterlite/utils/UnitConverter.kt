package com.example.unitconverterlite.utils

import android.content.Context
import com.example.unitconverterlite.R

object UnitConverter {

    fun convert(context: Context, value: Double, fromUnit: String, toUnit: String, type: String): Double {
        return when (type) {
            context.getString(R.string.length) -> convertLength(context, value, fromUnit, toUnit)
            context.getString(R.string.area) -> convertArea(context, value, fromUnit, toUnit)
            context.getString(R.string.time) -> convertTime(context, value, fromUnit, toUnit)
            context.getString(R.string.volume) -> convertVolume(context, value, fromUnit, toUnit)
            context.getString(R.string.temperature) -> convertTemperature(context, value, fromUnit, toUnit)
            context.getString(R.string.weight) -> convertWeight(context, value, fromUnit, toUnit)
            context.getString(R.string.speed) -> convertSpeed(context, value, fromUnit, toUnit)
            context.getString(R.string.energy) -> convertEnergy(context, value, fromUnit, toUnit)
            context.getString(R.string.power) -> convertPower(context, value, fromUnit, toUnit)
            context.getString(R.string.torque) -> convertTorque(context, value, fromUnit, toUnit)
            context.getString(R.string.pressure) -> convertPressure(context, value, fromUnit, toUnit)
            context.getString(R.string.force) -> convertForce(context, value, fromUnit, toUnit)
            context.getString(R.string.angle) -> convertAngle(context, value, fromUnit, toUnit)
            context.getString(R.string.currency) -> convertCurrency(context, value, fromUnit, toUnit)
            context.getString(R.string.sound) -> convertSound(context, value, fromUnit, toUnit)
            else -> value
        }
    }

    // ------------------ Length ------------------
    private fun convertLength(context: Context, value: Double, fromUnit: String, toUnit: String): Double {
        val meters = when (fromUnit) {
            context.getString(R.string.meter) -> value
            context.getString(R.string.kilometer) -> value * 1000
            context.getString(R.string.mile) -> value * 1609.34
            context.getString(R.string.feet) -> value * 0.3048
            context.getString(R.string.inch) -> value * 0.0254
            else -> value
        }
        return when (toUnit) {
            context.getString(R.string.meter) -> meters
            context.getString(R.string.kilometer) -> meters / 1000
            context.getString(R.string.mile) -> meters / 1609.34
            context.getString(R.string.feet) -> meters / 0.3048
            context.getString(R.string.inch) -> meters / 0.0254
            else -> meters
        }
    }

    // ------------------ Area ------------------
    private fun convertArea(context: Context, value: Double, fromUnit: String, toUnit: String): Double {
        val sqm = when (fromUnit) {
            context.getString(R.string.square_meter) -> value
            context.getString(R.string.square_kilometer) -> value * 1_000_000
            context.getString(R.string.square_mile) -> value * 2_589_988.11
            context.getString(R.string.hectare) -> value * 10_000
            context.getString(R.string.acre) -> value * 4046.86
            else -> value
        }
        return when (toUnit) {
            context.getString(R.string.square_meter) -> sqm
            context.getString(R.string.square_kilometer) -> sqm / 1_000_000
            context.getString(R.string.square_mile) -> sqm / 2_589_988.11
            context.getString(R.string.hectare) -> sqm / 10_000
            context.getString(R.string.acre) -> sqm / 4046.86
            else -> sqm
        }
    }

    // ------------------ Time ------------------
    private fun convertTime(context: Context, value: Double, fromUnit: String, toUnit: String): Double {
        val seconds = when (fromUnit) {
            context.getString(R.string.second) -> value
            context.getString(R.string.minute) -> value * 60
            context.getString(R.string.hour) -> value * 3600
            context.getString(R.string.day) -> value * 86400
            context.getString(R.string.week) -> value * 604800
            else -> value
        }
        return when (toUnit) {
            context.getString(R.string.second) -> seconds
            context.getString(R.string.minute) -> seconds / 60
            context.getString(R.string.hour) -> seconds / 3600
            context.getString(R.string.day) -> seconds / 86400
            context.getString(R.string.week) -> seconds / 604800
            else -> seconds
        }
    }

    // ------------------ Volume ------------------
    private fun convertVolume(context: Context, value: Double, fromUnit: String, toUnit: String): Double {
        val liters = when (fromUnit) {
            context.getString(R.string.liter) -> value
            context.getString(R.string.milliliter) -> value / 1000
            context.getString(R.string.gallon) -> value * 3.78541
            context.getString(R.string.cubic_meter) -> value * 1000
            context.getString(R.string.cubic_foot) -> value * 28.3168
            else -> value
        }
        return when (toUnit) {
            context.getString(R.string.liter) -> liters
            context.getString(R.string.milliliter) -> liters * 1000
            context.getString(R.string.gallon) -> liters / 3.78541
            context.getString(R.string.cubic_meter) -> liters / 1000
            context.getString(R.string.cubic_foot) -> liters / 28.3168
            else -> liters
        }
    }

    // ------------------ Temperature ------------------
    private fun convertTemperature(context: Context, value: Double, fromUnit: String, toUnit: String): Double {
        val celsius = when (fromUnit) {
            context.getString(R.string.celsius) -> value
            context.getString(R.string.fahrenheit) -> (value - 32) * 5 / 9
            context.getString(R.string.kelvin) -> value - 273.15
            else -> value
        }
        return when (toUnit) {
            context.getString(R.string.celsius) -> celsius
            context.getString(R.string.fahrenheit) -> celsius * 9 / 5 + 32
            context.getString(R.string.kelvin) -> celsius + 273.15
            else -> celsius
        }
    }

    // ------------------ Weight ------------------
    private fun convertWeight(context: Context, value: Double, fromUnit: String, toUnit: String): Double {
        val kg = when (fromUnit) {
            context.getString(R.string.kilogram) -> value
            context.getString(R.string.gram) -> value / 1000
            context.getString(R.string.pound) -> value * 0.453592
            context.getString(R.string.ounce) -> value * 0.0283495
            else -> value
        }
        return when (toUnit) {
            context.getString(R.string.kilogram) -> kg
            context.getString(R.string.gram) -> kg * 1000
            context.getString(R.string.pound) -> kg / 0.453592
            context.getString(R.string.ounce) -> kg / 0.0283495
            else -> kg
        }
    }

    // ------------------ Speed ------------------
    private fun convertSpeed(context: Context, value: Double, fromUnit: String, toUnit: String): Double {
        val mps = when (fromUnit) {
            context.getString(R.string.meter) -> value
            context.getString(R.string.kmph) -> value / 3.6
            context.getString(R.string.mph) -> value * 0.44704
            context.getString(R.string.fps) -> value * 0.3048
            else -> value
        }
        return when (toUnit) {
            context.getString(R.string.meter) -> mps
            context.getString(R.string.kmph) -> mps * 3.6
            context.getString(R.string.mph) -> mps / 0.44704
            context.getString(R.string.fps) -> mps / 0.3048
            else -> mps
        }
    }

    // ------------------ Energy ------------------
    private fun convertEnergy(context: Context, value: Double, fromUnit: String, toUnit: String): Double {
        val joules = when (fromUnit) {
            context.getString(R.string.joule) -> value
            context.getString(R.string.calorie) -> value * 4.184
            context.getString(R.string.kwh) -> value * 3_600_000
            context.getString(R.string.btu) -> value * 1055.06
            else -> value
        }
        return when (toUnit) {
            context.getString(R.string.joule) -> joules
            context.getString(R.string.calorie) -> joules / 4.184
            context.getString(R.string.kwh) -> joules / 3_600_000
            context.getString(R.string.btu) -> joules / 1055.06
            else -> joules
        }
    }

    // ------------------ Power ------------------
    private fun convertPower(context: Context, value: Double, fromUnit: String, toUnit: String): Double {
        val watt = when (fromUnit) {
            context.getString(R.string.watt) -> value
            context.getString(R.string.kilowatt) -> value * 1000
            context.getString(R.string.horsepower) -> value * 745.7
            else -> value
        }
        return when (toUnit) {
            context.getString(R.string.watt) -> watt
            context.getString(R.string.kilowatt) -> watt / 1000
            context.getString(R.string.horsepower) -> watt / 745.7
            else -> watt
        }
    }

    // ------------------ Torque ------------------
    private fun convertTorque(context: Context, value: Double, fromUnit: String, toUnit: String): Double {
        val nm = when (fromUnit) {
            context.getString(R.string.newton_meter) -> value
            context.getString(R.string.kilogram_meter) -> value * 9.80665
            context.getString(R.string.pound_foot) -> value * 1.35582
            else -> value
        }
        return when (toUnit) {
            context.getString(R.string.newton_meter) -> nm
            context.getString(R.string.kilogram_meter) -> nm / 9.80665
            context.getString(R.string.pound_foot) -> nm / 1.35582
            else -> nm
        }
    }

    // ------------------ Pressure ------------------
    private fun convertPressure(context: Context, value: Double, fromUnit: String, toUnit: String): Double {
        val pa = when (fromUnit) {
            context.getString(R.string.pascal) -> value
            context.getString(R.string.kilopascal) -> value * 1000
            context.getString(R.string.bar) -> value * 100000
            context.getString(R.string.psi) -> value * 6894.76
            else -> value
        }
        return when (toUnit) {
            context.getString(R.string.pascal) -> pa
            context.getString(R.string.kilopascal) -> pa / 1000
            context.getString(R.string.bar) -> pa / 100000
            context.getString(R.string.psi) -> pa / 6894.76
            else -> pa
        }
    }

    // ------------------ Force ------------------
    private fun convertForce(context: Context, value: Double, fromUnit: String, toUnit: String): Double {
        val newton = when (fromUnit) {
            context.getString(R.string.newton) -> value
            context.getString(R.string.kilogram_force) -> value * 9.80665
            context.getString(R.string.pound_force) -> value * 4.44822
            else -> value
        }
        return when (toUnit) {
            context.getString(R.string.newton) -> newton
            context.getString(R.string.kilogram_force) -> newton / 9.80665
            context.getString(R.string.pound_force) -> newton / 4.44822
            else -> newton
        }
    }

    // ------------------ Angle ------------------
    private fun convertAngle(context: Context, value: Double, fromUnit: String, toUnit: String): Double {
        val degree = when (fromUnit) {
            context.getString(R.string.degree) -> value
            context.getString(R.string.radian) -> value * 180 / Math.PI
            context.getString(R.string.gradian) -> value * 0.9
            else -> value
        }
        return when (toUnit) {
            context.getString(R.string.degree) -> degree
            context.getString(R.string.radian) -> degree * Math.PI / 180
            context.getString(R.string.gradian) -> degree / 0.9
            else -> degree
        }
    }

    // ------------------ Currency ------------------
    private fun convertCurrency(context: Context, value: Double, fromUnit: String, toUnit: String): Double {
        val rates = mapOf(
            context.getString(R.string.usd) to 1.0,
            context.getString(R.string.eur) to 0.93,
            context.getString(R.string.gbp) to 0.81,
            context.getString(R.string.jpy) to 136.0,
            context.getString(R.string.pkr) to 280.0
        )
        val usdValue = value / (rates[fromUnit] ?: 1.0)
        return usdValue * (rates[toUnit] ?: 1.0)
    }

    // ------------------ Sound ------------------
    private fun convertSound(context: Context, value: Double, fromUnit: String, toUnit: String): Double {

        // -------- Frequency --------
        if (fromUnit in listOf(context.getString(R.string.hertz), context.getString(R.string.kilohertz), context.getString(R.string.megahertz)) &&
            toUnit in listOf(context.getString(R.string.hertz), context.getString(R.string.kilohertz), context.getString(R.string.megahertz))
        ) {
            val hz = when (fromUnit) {
                context.getString(R.string.hertz) -> value
                context.getString(R.string.kilohertz) -> value * 1_000
                context.getString(R.string.megahertz) -> value * 1_000_000
                else -> value
            }
            return when (toUnit) {
                context.getString(R.string.hertz) -> hz
                context.getString(R.string.kilohertz) -> hz / 1_000
                context.getString(R.string.megahertz) -> hz / 1_000_000
                else -> hz
            }
        }

        // -------- Sound Pressure --------
        if (fromUnit in listOf(context.getString(R.string.pascal), context.getString(R.string.micropascal)) &&
            toUnit in listOf(context.getString(R.string.pascal), context.getString(R.string.micropascal))
        ) {
            val pa = when (fromUnit) {
                context.getString(R.string.pascal) -> value
                context.getString(R.string.micropascal) -> value / 1_000_000
                else -> value
            }
            return when (toUnit) {
                context.getString(R.string.pascal) -> pa
                context.getString(R.string.micropascal) -> pa * 1_000_000
                else -> pa
            }
        }

        // -------- Decibel / Watt per sq meter (no conversion) --------
        if ((fromUnit == context.getString(R.string.decibel) && toUnit == context.getString(R.string.decibel)) ||
            (fromUnit == context.getString(R.string.watt_per_square_meter) && toUnit == context.getString(R.string.watt_per_square_meter))
        ) return value

        return value
    }

}
