package com.example.unitconverterlite.Adaptor


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unitconverterlite.R
import com.example.unitconverterlite.DataClass.HistoryItem

class HistoryAdaptor(
    private var items: List<HistoryItem>
) : RecyclerView.Adapter<HistoryAdaptor.HistoryViewHolder>() {

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val valueGiven: TextView = itemView.findViewById(R.id.value_given)
        val valueGivenUnit: TextView = itemView.findViewById(R.id.value_given_unit)
        val valueReceived: TextView = itemView.findViewById(R.id.value_recieved)
        val valueReceivedUnit: TextView = itemView.findViewById(R.id.value_recieved_unit)
        val unitName: TextView = itemView.findViewById(R.id.unit_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item_view, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = items[position]
        holder.valueGiven.text = item.valueGiven
        holder.valueGivenUnit.text = holder.itemView.getUnitString(item.valueGivenUnit)
        holder.valueReceived.text = item.valueReceived
        holder.valueReceivedUnit.text = holder.itemView.getUnitString(item.valueReceivedUnit)
        holder.unitName.text = holder.itemView.getUnitString(item.unitName)
    }

    override fun getItemCount(): Int = items.size

    fun submitList(list: List<HistoryItem>) {
        items = list
        notifyDataSetChanged()
    }

    fun View.getUnitString(key: String): String {
        return when (key.lowercase()) {
            // Categories / Unit types
            "length" -> context.getString(R.string.length)
            "weight" -> context.getString(R.string.weight)
            "area" -> context.getString(R.string.area)
            "volume" -> context.getString(R.string.volume)
            "time" -> context.getString(R.string.time)
            "temperature" -> context.getString(R.string.temperature)
            "speed" -> context.getString(R.string.speed)
            "energy" -> context.getString(R.string.energy)
            "power" -> context.getString(R.string.power)
            "torque" -> context.getString(R.string.torque)
            "pressure" -> context.getString(R.string.pressure)
            "force" -> context.getString(R.string.force)
            "angle" -> context.getString(R.string.angle)
            "ratio" -> context.getString(R.string.ratio)
            "currency" -> context.getString(R.string.currency)
            "percentage" -> context.getString(R.string.percentage)
            "sound" -> context.getString(R.string.sound)
            "bmi" -> context.getString(R.string.bmi)

            // Length units
            "meter" -> context.getString(R.string.meter)
            "kilometer" -> context.getString(R.string.kilometer)
            "mile" -> context.getString(R.string.mile)
            "foot" -> context.getString(R.string.foot)
            "feet" -> context.getString(R.string.feet)
            "inch" -> context.getString(R.string.inch)

            // Area units
            "square_meter" -> context.getString(R.string.square_meter)
            "square_kilometer" -> context.getString(R.string.square_kilometer)
            "square_mile" -> context.getString(R.string.square_mile)
            "hectare" -> context.getString(R.string.hectare)
            "acre" -> context.getString(R.string.acre)

            // Volume units
            "liter" -> context.getString(R.string.liter)
            "milliliter" -> context.getString(R.string.milliliter)
            "gallon" -> context.getString(R.string.gallon)
            "cubic_meter" -> context.getString(R.string.cubic_meter)
            "cubic_foot" -> context.getString(R.string.cubic_foot)

            // Weight units
            "kilogram" -> context.getString(R.string.kilogram)
            "gram" -> context.getString(R.string.gram)
            "pound" -> context.getString(R.string.pound)
            "ounce" -> context.getString(R.string.ounce)

            // Temperature units
            "celsius" -> context.getString(R.string.celsius)
            "fahrenheit" -> context.getString(R.string.fahrenheit)
            "kelvin" -> context.getString(R.string.kelvin)

            // Time units
            "second" -> context.getString(R.string.second)
            "minute" -> context.getString(R.string.minute)
            "hour" -> context.getString(R.string.hour)
            "day" -> context.getString(R.string.day)
            "week" -> context.getString(R.string.week)

            // Speed units
            "mps" -> context.getString(R.string.mps)
            "kmph" -> context.getString(R.string.kmph)
            "mph" -> context.getString(R.string.mph)
            "fps" -> context.getString(R.string.fps)

            // Energy units
            "joule" -> context.getString(R.string.joule)
            "calorie" -> context.getString(R.string.calorie)
            "kwh" -> context.getString(R.string.kwh)
            "btu" -> context.getString(R.string.btu)

            // Power units
            "watt" -> context.getString(R.string.watt)
            "kilowatt" -> context.getString(R.string.kilowatt)
            "horsepower" -> context.getString(R.string.horsepower)

            // Torque units
            "newton_meter" -> context.getString(R.string.newton_meter)
            "kilogram_meter" -> context.getString(R.string.kilogram_meter)
            "pound_foot" -> context.getString(R.string.pound_foot)

            // Pressure units
            "pascal" -> context.getString(R.string.pascal)
            "kilopascal" -> context.getString(R.string.kilopascal)
            "bar" -> context.getString(R.string.bar)
            "psi" -> context.getString(R.string.psi)

            // Force units
            "newton" -> context.getString(R.string.newton)
            "kilogram_force" -> context.getString(R.string.kilogram_force)
            "pound_force" -> context.getString(R.string.pound_force)

            // Angle units
            "degree" -> context.getString(R.string.degree)
            "radian" -> context.getString(R.string.radian)
            "gradian" -> context.getString(R.string.gradian)

            // Currency
            "usd" -> context.getString(R.string.usd)
            "eur" -> context.getString(R.string.eur)
            "gbp" -> context.getString(R.string.gbp)
            "jpy" -> context.getString(R.string.jpy)
            "pkr" -> context.getString(R.string.pkr)

            // Sound
            "hertz" -> context.getString(R.string.hertz)
            "kilohertz" -> context.getString(R.string.kilohertz)
            "megahertz" -> context.getString(R.string.megahertz)
            "pascal_unit" -> context.getString(R.string.pascal_unit)
            "micropascal" -> context.getString(R.string.micropascal)
            "decibel" -> context.getString(R.string.decibel)
            "watt_per_square_meter" -> context.getString(R.string.watt_per_square_meter)

            // BMI
            "metric" -> context.getString(R.string.metric)
            "imperial" -> context.getString(R.string.imperial)

            else -> key
        }
    }



}

