package com.example.unitconverterlite.Adaptor


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.unitconverterlite.R
import com.example.unitconverterlite.DataClass.HistoryItem

class HistoryAdapter(private val items: List<HistoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val valueGiven: TextView = itemView.findViewById(R.id.value_given)
        val valueReceived: TextView = itemView.findViewById(R.id.value_recieved)
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
        holder.valueReceived.text = item.valueReceived
        holder.unitName.text = item.unitName
    }

    override fun getItemCount(): Int = items.size
}
