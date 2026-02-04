package com.example.unitconverterlite.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unitconverterlite.Adaptor.HomeAdapter
import com.example.unitconverterlite.DataClass.CardItem
import com.example.unitconverterlite.DataClass.HomeItem
import com.example.unitconverterlite.MainActivity
import com.example.unitconverterlite.R

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

        fun newUnitConverterFragment() = UnitConverterFragment()

        val items = listOf(
            HomeItem.Header(getString(R.string.header_everyday)),
            HomeItem.Card(CardItem(getString(R.string.length), R.drawable.length_icon_new, newUnitConverterFragment())),
            HomeItem.Card(CardItem(getString(R.string.area), R.drawable.area_icon, newUnitConverterFragment())),
            HomeItem.Card(CardItem(getString(R.string.time), R.drawable.time_icon, newUnitConverterFragment())),
            HomeItem.Card(CardItem(getString(R.string.volume), R.drawable.volume_icon, newUnitConverterFragment())),
            HomeItem.Card(CardItem(getString(R.string.temperature), R.drawable.temp_icon, newUnitConverterFragment())),
            HomeItem.Card(CardItem(getString(R.string.weight), R.drawable.weight_icon, newUnitConverterFragment())),
            HomeItem.Card(CardItem(getString(R.string.speed), R.drawable.speed_icon, newUnitConverterFragment())),
            HomeItem.Card(CardItem(getString(R.string.energy), R.drawable.energy_icon, newUnitConverterFragment())),

            HomeItem.Header(getString(R.string.header_mechanical)),
            HomeItem.Card(CardItem(getString(R.string.power), R.drawable.power_icon, newUnitConverterFragment())),
            HomeItem.Card(CardItem(getString(R.string.torque), R.drawable.torque_icon, newUnitConverterFragment())),
            HomeItem.Card(CardItem(getString(R.string.pressure), R.drawable.pressure_icon, newUnitConverterFragment())),
            HomeItem.Card(CardItem(getString(R.string.energy), R.drawable.energy_icon, newUnitConverterFragment())),
            HomeItem.Card(CardItem(getString(R.string.angle), R.drawable.angle_icon, newUnitConverterFragment())),

            HomeItem.Header(getString(R.string.header_finance)),
            HomeItem.Card(CardItem(getString(R.string.ratio), R.drawable.ratio_icon, newUnitConverterFragment())),
            HomeItem.Card(CardItem(getString(R.string.currency), R.drawable.currency_icon, newUnitConverterFragment())),
            HomeItem.Card(CardItem(getString(R.string.percentage), R.drawable.percentage_icon, newUnitConverterFragment())),

            HomeItem.Header(getString(R.string.header_special)),
            HomeItem.Card(CardItem(getString(R.string.sound), R.drawable.sound_icon, newUnitConverterFragment())),
            HomeItem.Card(CardItem(getString(R.string.bmi), R.drawable.bmi_icon, newUnitConverterFragment()))
        )

        val adapter = HomeAdapter(items, parentFragmentManager)
        val layoutManager = GridLayoutManager(requireContext(), 3)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val item = adapter.items[position]
                return if (item is HomeItem.Header) 3 else 1
            }
        }

        recycler.layoutManager = layoutManager
        recycler.adapter = adapter

        val searchView = view.findViewById<EditText>(R.id.searchView_home)
        searchView.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter(s.toString())
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })

        return view
    }
}
