package com.example.unitconverterlite.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unitconverterlite.Adaptor.HistoryAdapter
import com.example.unitconverterlite.DataClass.HistoryItem
import com.example.unitconverterlite.R


class HistoryFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.history_rv)
        recyclerView.adapter = HistoryAdapter(getDummyData())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view
    }


    private fun getDummyData(): List<HistoryItem> {
        return listOf(
            HistoryItem("100 kilometer", "62.137 miles", "Length"),
            HistoryItem("50 kilometer", "31.068 miles", "Length"),
            HistoryItem("200 kilometer", "124.274 miles", "Length"),
            HistoryItem("10 meter", "32.808 feet", "Length"),
            HistoryItem("500 meter", "1640.42 feet", "Length"),
            HistoryItem("1000 meter", "3280.84 feet", "Length"),
            HistoryItem("2 kilometer", "1.2427 miles", "Length"),
            HistoryItem("5 kilometer", "3.1069 miles", "Length"),
            HistoryItem("20 kilometer", "12.427 miles", "Length"),
            HistoryItem("15 kilometer", "9.3206 miles", "Length"),
            HistoryItem("25 kilometer", "15.534 miles", "Length"),
            HistoryItem("30 kilometer", "18.641 miles", "Length"),
            HistoryItem("40 kilometer", "24.855 miles", "Length"),
            HistoryItem("60 kilometer", "37.282 miles", "Length"),
            HistoryItem("80 kilometer", "49.709 miles", "Length"),
            HistoryItem("120 kilometer", "74.564 miles", "Length"),
            HistoryItem("150 kilometer", "93.206 miles", "Length"),
            HistoryItem("180 kilometer", "111.847 miles", "Length"),
            HistoryItem("200 kilometer", "124.274 miles", "Length"),
            HistoryItem("250 kilometer", "155.342 miles", "Length")
        )
    }

}