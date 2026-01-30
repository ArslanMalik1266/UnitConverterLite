package com.example.unitconverterlite.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unitconverterlite.Adaptor.HomeAdapter
import com.example.unitconverterlite.DataClass.CardItem
import com.example.unitconverterlite.DataClass.HomeItem
import com.example.unitconverterlite.MainActivity
import com.example.unitconverterlite.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class Home : Fragment() {

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.showBottomNav(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val recycler = view.findViewById<RecyclerView>(R.id.homeRecycler)

        val items = listOf(

            HomeItem.Header("Everyday Measurements"),
            HomeItem.Card(CardItem("Length", R.drawable.length_icon_new, UnitConverterFragment())),
            HomeItem.Card(CardItem("Area", R.drawable.area_icon, UnitConverterFragment())),
            HomeItem.Card(CardItem("Time", R.drawable.time_icon, UnitConverterFragment())),
            HomeItem.Card(CardItem("Volume", R.drawable.volume_icon, UnitConverterFragment())),
            HomeItem.Card(CardItem("Temperature", R.drawable.temp_icon, UnitConverterFragment())),
            HomeItem.Card(CardItem("Weight", R.drawable.weight_icon, UnitConverterFragment())),
            HomeItem.Card(CardItem("Speed", R.drawable.speed_icon, UnitConverterFragment())),
            HomeItem.Card(CardItem("Energy", R.drawable.energy_icon, UnitConverterFragment())),

            HomeItem.Header("Mechanical Units"),
            HomeItem.Card(CardItem("Power", R.drawable.power_icon, UnitConverterFragment())),
            HomeItem.Card(CardItem("Torque", R.drawable.torque_icon, UnitConverterFragment())),
            HomeItem.Card(CardItem("Pressure", R.drawable.pressure_icon, UnitConverterFragment())),
            HomeItem.Card(CardItem("Force", R.drawable.energy_icon, UnitConverterFragment())),
            HomeItem.Card(CardItem("Angle", R.drawable.angle_icon, UnitConverterFragment())),

            HomeItem.Header("Finance"),
            HomeItem.Card(CardItem("Ratio", R.drawable.ratio_icon, UnitConverterFragment())),
            HomeItem.Card(CardItem("Currency", R.drawable.currency_icon, UnitConverterFragment())),
            HomeItem.Card(CardItem("Percentage", R.drawable.percentage_icon, UnitConverterFragment())),

            HomeItem.Header("Special"),
            HomeItem.Card(CardItem("Sound", R.drawable.sound_icon, UnitConverterFragment())),
            HomeItem.Card(CardItem("BMI", R.drawable.bmi_icon, UnitConverterFragment()))
        )

        val layoutManager = GridLayoutManager(requireContext(), 3)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (items[position] is HomeItem.Header) 3 else 1
            }
        }

        recycler.layoutManager = layoutManager
        recycler.adapter = HomeAdapter(items, parentFragmentManager)

        return view
    }
}
