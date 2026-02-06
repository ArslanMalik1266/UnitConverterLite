package com.example.unitconverterlite.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unitconverterlite.Adaptor.HomeAdapter
import com.example.unitconverterlite.DataClass.CardItem
import com.example.unitconverterlite.DataClass.HomeItem
import com.example.unitconverterlite.MainActivity
import com.example.unitconverterlite.R

class Home : Fragment() {
    private lateinit var searchView : EditText

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
        searchView = view.findViewById(R.id.searchView_home)

        view.setOnTouchListener { _, _ ->
            if (searchView.isFocused) {
                searchView.clearFocus()
                hideKeyboard()
            }
            false
        }
        recycler.setOnTouchListener { _, _ ->
            if (searchView.isFocused) {
                searchView.clearFocus()
                hideKeyboard()
            }
            false
        }

        fun newUnitConverterFragment() = UnitConverterFragment()



        val items = listOf(

            HomeItem.Header(getString(R.string.header_everyday)),

            HomeItem.Card(
                CardItem(
                    getString(R.string.length),
                    R.drawable.length_icon_new,
                    newUnitConverterFragment(),
                    listOf(
                        getString(R.string.meter),
                        getString(R.string.kilometer),
                        getString(R.string.mile),
                        getString(R.string.foot),
                        getString(R.string.feet),
                        getString(R.string.inch)
                    )
                )
            ),

            HomeItem.Card(
                CardItem(
                    getString(R.string.area),
                    R.drawable.area_icon,
                    newUnitConverterFragment(),
                    listOf(
                        getString(R.string.square_meter),
                        getString(R.string.square_kilometer),
                        getString(R.string.square_mile),
                        getString(R.string.hectare),
                        getString(R.string.acre)
                    )
                )
            ),

            HomeItem.Card(
                CardItem(
                    getString(R.string.time),
                    R.drawable.time_icon,
                    newUnitConverterFragment(),
                    listOf(
                        getString(R.string.second),
                        getString(R.string.minute),
                        getString(R.string.hour),
                        getString(R.string.day),
                        getString(R.string.week)
                    )
                )
            ),

            HomeItem.Card(
                CardItem(
                    getString(R.string.volume),
                    R.drawable.volume_icon,
                    newUnitConverterFragment(),
                    listOf(
                        getString(R.string.liter),
                        getString(R.string.milliliter),
                        getString(R.string.gallon),
                        getString(R.string.cubic_meter),
                        getString(R.string.cubic_foot)
                    )
                )
            ),

            HomeItem.Card(
                CardItem(
                    getString(R.string.temperature),
                    R.drawable.temp_icon,
                    newUnitConverterFragment(),
                    listOf(
                        getString(R.string.celsius),
                        getString(R.string.fahrenheit),
                        getString(R.string.kelvin)
                    )
                )
            ),

            HomeItem.Card(
                CardItem(
                    getString(R.string.weight),
                    R.drawable.weight_icon,
                    newUnitConverterFragment(),
                    listOf(
                        getString(R.string.kilogram),
                        getString(R.string.gram),
                        getString(R.string.pound),
                        getString(R.string.ounce)
                    )
                )
            ),

            HomeItem.Card(
                CardItem(
                    getString(R.string.speed),
                    R.drawable.speed_icon,
                    newUnitConverterFragment(),
                    listOf(
                        getString(R.string.meters_per_second),
                        getString(R.string.kilometers_per_hour),
                        getString(R.string.miles_per_hour),
                        getString(R.string.feet_per_second)
                    )
                )
            ),

            HomeItem.Card(
                CardItem(
                    getString(R.string.energy),
                    R.drawable.energy_icon,
                    newUnitConverterFragment(),
                    listOf(
                        getString(R.string.joule),
                        getString(R.string.calorie),
                        getString(R.string.kwh),
                        getString(R.string.btu)
                    )
                )
            ),

            HomeItem.Header(getString(R.string.header_mechanical)),

            HomeItem.Card(
                CardItem(
                    getString(R.string.power),
                    R.drawable.power_icon,
                    newUnitConverterFragment(),
                    listOf(
                        getString(R.string.watt),
                        getString(R.string.kilowatt),
                        getString(R.string.horsepower)
                    )
                )
            ),

            HomeItem.Card(
                CardItem(
                    getString(R.string.torque),
                    R.drawable.torque_icon,
                    newUnitConverterFragment(),
                    listOf(
                        getString(R.string.newton_meter),
                        getString(R.string.kilogram_meter),
                        getString(R.string.pound_foot)
                    )
                )
            ),

            HomeItem.Card(
                CardItem(
                    getString(R.string.pressure),
                    R.drawable.pressure_icon,
                    newUnitConverterFragment(),
                    listOf(
                        getString(R.string.pascal),
                        getString(R.string.kilopascal),
                        getString(R.string.bar),
                        getString(R.string.psi)
                    )
                )
            ),

            HomeItem.Card(
                CardItem(
                    getString(R.string.angle),
                    R.drawable.angle_icon,
                    newUnitConverterFragment(),
                    listOf(
                        getString(R.string.degree),
                        getString(R.string.radian),
                        getString(R.string.gradian)
                    )
                )
            ),

            HomeItem.Header(getString(R.string.header_finance)),

            HomeItem.Card(
                CardItem(
                    getString(R.string.ratio),
                    R.drawable.ratio_icon,
                    newUnitConverterFragment(),
                    listOf(
                        getString(R.string.ratio),
                    )
                )
            ),

            HomeItem.Card(
                CardItem(
                    getString(R.string.currency),
                    R.drawable.currency_icon,
                    newUnitConverterFragment(),
                    listOf(
                        getString(R.string.usd),
                        getString(R.string.eur),
                        getString(R.string.gbp),
                        getString(R.string.jpy),
                        getString(R.string.pkr)
                    )
                )
            ),

            HomeItem.Card(
                CardItem(
                    getString(R.string.percentage),
                    R.drawable.percentage_icon,
                    newUnitConverterFragment(),
                    listOf(
                        getString(R.string.percentage),
                        "%"
                    )
                )
            ),

            HomeItem.Header(getString(R.string.header_special)),

            HomeItem.Card(
                CardItem(
                    getString(R.string.sound),
                    R.drawable.sound_icon,
                    newUnitConverterFragment(),
                    listOf(
                        getString(R.string.hertz),
                        getString(R.string.kilohertz),
                        getString(R.string.megahertz),
                        getString(R.string.decibel),
                        getString(R.string.micropascal),
                        getString(R.string.watt_per_square_meter)
                    )
                )
            ),

            HomeItem.Card(
                CardItem(
                    getString(R.string.bmi),
                    R.drawable.bmi_icon,
                    newUnitConverterFragment(),
                    listOf(
                        getString(R.string.metric),
                        getString(R.string.imperial))
                )
            )
        )



        val adapter = HomeAdapter(items, parentFragmentManager){
            searchView.clearFocus()
            hideKeyboard()
        }
        val layoutManager = GridLayoutManager(requireContext(), 3)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val item = adapter.items[position]
                return if (item is HomeItem.Header) 3 else 1
            }
        }

        recycler.layoutManager = layoutManager
        recycler.adapter = adapter

        view.setOnClickListener {
            if (searchView.isFocused) {
                searchView.clearFocus()
                hideKeyboard()
            }
        }

        searchView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                v.clearFocus()
                hideKeyboard()
                true
            } else {
                false
            }
        }

        searchView.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter(s.toString())
            }

            override fun afterTextChanged(s: android.text.Editable?) {}
        })



        return view
    }
    private fun hideKeyboard() {
        val imm = requireContext()
            .getSystemService(android.content.Context.INPUT_METHOD_SERVICE)
                as android.view.inputmethod.InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}
