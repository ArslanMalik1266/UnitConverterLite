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

            // Categories
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

            // Length
            "meter (m)" -> context.getString(R.string.meter)
            "kilometer (km)" -> context.getString(R.string.kilometer)
            "mile (mi)" -> context.getString(R.string.mile)
            "foot (ft)" -> context.getString(R.string.foot)
            "inch (in)" -> context.getString(R.string.inch)

            // Area
            "square meter (m²)" -> context.getString(R.string.square_meter)
            "square kilometer (km²)" -> context.getString(R.string.square_kilometer)
            "square mile (mi²)" -> context.getString(R.string.square_mile)
            "hectare (ha)" -> context.getString(R.string.hectare)
            "acre (ac)" -> context.getString(R.string.acre)

            // Volume
            "liter (l)" -> context.getString(R.string.liter)
            "milliliter (ml)" -> context.getString(R.string.milliliter)
            "gallon (gal)" -> context.getString(R.string.gallon)
            "cubic meter (m³)" -> context.getString(R.string.cubic_meter)
            "cubic foot (ft³)" -> context.getString(R.string.cubic_foot)

            // Weight
            "kilogram (kg)" -> context.getString(R.string.kilogram)
            "gram (g)" -> context.getString(R.string.gram)
            "pound (lb)" -> context.getString(R.string.pound)
            "ounce (oz)" -> context.getString(R.string.ounce)

            // Temperature
            "celsius (°c)" -> context.getString(R.string.celsius)
            "fahrenheit (°f)" -> context.getString(R.string.fahrenheit)
            "kelvin (k)" -> context.getString(R.string.kelvin)

            // Time
            "second (s)" -> context.getString(R.string.second)
            "minute (min)" -> context.getString(R.string.minute)
            "hour (h)" -> context.getString(R.string.hour)
            "day (d)" -> context.getString(R.string.day)
            "week (wk)" -> context.getString(R.string.week)

            // Speed
            "meters per second (m/s)" -> context.getString(R.string.meters_per_second)
            "kilometers per hour (km/h)" -> context.getString(R.string.kilometers_per_hour)
            "miles per hour (mph)" -> context.getString(R.string.miles_per_hour)
            "feet per second (ft/s)" -> context.getString(R.string.feet_per_second)

            // Energy
            "joule (j)" -> context.getString(R.string.joule)
            "calorie (cal)" -> context.getString(R.string.calorie)
            "kilowatt-hour (kwh)" -> context.getString(R.string.kwh)
            "british thermal unit (btu)" -> context.getString(R.string.btu)

            // Power
            "watt (w)" -> context.getString(R.string.watt)
            "kilowatt (kw)" -> context.getString(R.string.kilowatt)
            "horsepower (hp)" -> context.getString(R.string.horsepower)

            // Torque
            "newton meter (n·m)" -> context.getString(R.string.newton_meter)
            "kilogram meter (kg·m)" -> context.getString(R.string.kilogram_meter)
            "pound foot (lb·ft)" -> context.getString(R.string.pound_foot)

            // Pressure
            "pascal (pa)" -> context.getString(R.string.pascal)
            "kilopascal (kpa)" -> context.getString(R.string.kilopascal)
            "bar (bar)" -> context.getString(R.string.bar)
            "pounds per square inch (psi)" -> context.getString(R.string.psi)

            // Force
            "newton (n)" -> context.getString(R.string.newton)
            "kilogram-force (kgf)" -> context.getString(R.string.kilogram_force)
            "pound-force (lbf)" -> context.getString(R.string.pound_force)

            // Angle
            "degree (°)" -> context.getString(R.string.degree)
            "radian (rad)" -> context.getString(R.string.radian)
            "gradian (gon)" -> context.getString(R.string.gradian)

            // Currency
            "us dollar (usd)" -> context.getString(R.string.usd)
            "euro (eur)" -> context.getString(R.string.eur)
            "british pound (gbp)" -> context.getString(R.string.gbp)
            "japanese yen (jpy)" -> context.getString(R.string.jpy)
            "pakistani rupee (pkr)" -> context.getString(R.string.pkr)

            // Sound
            "hertz (hz)" -> context.getString(R.string.hertz)
            "kilohertz (khz)" -> context.getString(R.string.kilohertz)
            "megahertz (mhz)" -> context.getString(R.string.megahertz)
            "micropascal (µpa)" -> context.getString(R.string.micropascal)
            "decibel (db)" -> context.getString(R.string.decibel)
            "watt per square meter (w/m²)" -> context.getString(R.string.watt_per_square_meter)

            // BMI
            "metric (kg/m²)" -> context.getString(R.string.metric)
            "imperial (lb/in²)" -> context.getString(R.string.imperial)

            else -> key
        }
    }




}

