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
import com.example.unitconverterlite.DataClass.CardItem
import com.example.unitconverterlite.MainActivity
import com.example.unitconverterlite.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class Home : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.showBottomNav(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val everydayGrid = view.findViewById<GridLayout>(R.id.grid_everyday)
        val everydayCards = listOf(
            CardItem("Length", R.drawable.length_icon_new, UnitConverterFragment()),
            CardItem("Area", R.drawable.area_icon, UnitConverterFragment()),
            CardItem("Time", R.drawable.time_icon, UnitConverterFragment()),
            CardItem("Volume", R.drawable.volume_icon, UnitConverterFragment()),
            CardItem("Temperature", R.drawable.temp_icon, UnitConverterFragment()),
            CardItem("Weight", R.drawable.weight_icon, UnitConverterFragment()),
            CardItem("Speed", R.drawable.speed_icon, UnitConverterFragment()),
            CardItem("Energy", R.drawable.energy_icon, UnitConverterFragment())
        )
        addCardsToGrid(everydayGrid, everydayCards)

        val mechGrid = view.findViewById<GridLayout>(R.id.grid_mechanical)
        val mechCards = listOf(
            CardItem("Power", R.drawable.power_icon, UnitConverterFragment()),
            CardItem("Torque", R.drawable.torque_icon, UnitConverterFragment()),
            CardItem("Pressure", R.drawable.pressure_icon, UnitConverterFragment()),
            CardItem("Force", R.drawable.energy_icon, UnitConverterFragment()),
            CardItem("Angle", R.drawable.angle_icon, UnitConverterFragment())
        )
        addCardsToGrid(mechGrid, mechCards)

        val financeGrid = view.findViewById<GridLayout>(R.id.grid_finance)
        val financeCards = listOf(
            CardItem("Ratio", R.drawable.ratio_icon, UnitConverterFragment()),
            CardItem("Currency", R.drawable.currency_icon, UnitConverterFragment()),
            CardItem("Percentage", R.drawable.percentage_icon, UnitConverterFragment())
        )
        addCardsToGrid(financeGrid, financeCards)

        val specialGrid = view.findViewById<GridLayout>(R.id.grid_special)
        val specialCards = listOf(
            CardItem("Sound", R.drawable.sound_icon, UnitConverterFragment()),
            CardItem("BMI", R.drawable.bmi_icon, UnitConverterFragment())
        )
        addCardsToGrid(specialGrid, specialCards)


        return view
    }

    private fun addCardsToGrid(grid: GridLayout, cards: List<CardItem>) {
        val columnCount = 3
        grid.columnCount = columnCount

        cards.forEachIndexed { index, item ->
            val card = layoutInflater.inflate(R.layout.item_card_layout, grid, false) as CardView

            val titleView = card.findViewById<TextView>(R.id.title)
            val iconView = card.findViewById<ImageView>(R.id.icon)
            titleView.text = item.title

            val drawable = AppCompatResources.getDrawable(requireContext(), item.iconRes)
            iconView.setImageDrawable(drawable)

            val row = index / columnCount
            val col = index % columnCount
            val params = GridLayout.LayoutParams(
                GridLayout.spec(row, 1f),
                GridLayout.spec(col, 1f)
            ).apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                setMargins(12, 12, 12, 12)
            }
            card.layoutParams = params


            card.setOnClickListener {
                val fragment = item.fragment
                val bundle = Bundle().apply {
                    putString("title", item.title)
                    putString("type", item.title)
                }
                fragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_container, item.fragment)
                    .addToBackStack("UnitConverter")
                    .commit()
            }

            grid.addView(card)
        }

    }


}