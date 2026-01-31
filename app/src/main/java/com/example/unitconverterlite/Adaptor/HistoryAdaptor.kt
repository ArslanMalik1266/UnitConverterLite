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
        holder.valueGivenUnit.text = item.valueGivenUnit
        holder.valueReceived.text = item.valueReceived
        holder.valueReceivedUnit.text = item.valueReceivedUnit
        holder.unitName.text = item.unitName
    }

    override fun getItemCount(): Int = items.size

    fun submitList(list: List<HistoryItem>) {
        items = list
        notifyDataSetChanged()
    }


}

