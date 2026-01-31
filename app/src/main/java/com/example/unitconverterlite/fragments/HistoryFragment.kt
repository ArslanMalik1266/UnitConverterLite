package com.example.unitconverterlite.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unitconverterlite.Adaptor.HistoryAdaptor
import com.example.unitconverterlite.DataClass.HistoryItem
import com.example.unitconverterlite.MainActivity
import com.example.unitconverterlite.R
import com.example.unitconverterlite.viewModel.HistoryViewModel
import com.google.android.material.appbar.MaterialToolbar
import androidx.lifecycle.ViewModelProvider

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var historyAdapter: HistoryAdaptor
    private lateinit var recyclerView: RecyclerView

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.showBottomNav(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        recyclerView = view.findViewById(R.id.history_rv)
        historyAdapter = HistoryAdaptor(emptyList())
        recyclerView.adapter = historyAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        historyViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[HistoryViewModel::class.java]

        historyViewModel.history.observe(viewLifecycleOwner) { list: List<HistoryItem> ->
            historyAdapter.submitList(list)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.topAppBar)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}
