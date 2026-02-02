package com.example.unitconverterlite.Adaptor

import android.os.Bundle
import android.provider.Settings.Global.putString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unitconverterlite.DataClass.HomeItem
import com.example.unitconverterlite.R

class HomeAdapter(
    internal var items: List<HomeItem>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var fullList: List<HomeItem> = items.toList()

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_CARD = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is HomeItem.Header -> TYPE_HEADER
            is HomeItem.Card -> TYPE_CARD
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_header, parent, false)
            HeaderVH(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_card_layout, parent, false)
            CardVH(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (val item = items[position]) {

            is HomeItem.Header -> {
                (holder as HeaderVH).title.text = item.title
            }

            is HomeItem.Card -> {
                val cardItem = item.cardItem
                val h = holder as CardVH

                h.title.text = cardItem.title
                h.icon.setImageResource(cardItem.iconRes)

                h.itemView.setOnClickListener {
                    val bundle = Bundle().apply {
                        putString("title", cardItem.title)
                        putString("type", cardItem.title)
                    }
                    cardItem.fragment.arguments = bundle

                    fragmentManager.beginTransaction()
                        .replace(R.id.main_container, cardItem.fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }

    override fun getItemCount() = items.size

    class HeaderVH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view as TextView
    }

    class CardVH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val icon: ImageView = view.findViewById(R.id.icon)
    }

    fun filter(query: String) {
        if (query.isEmpty()) {
            items = fullList
        } else {
            val filtered = mutableListOf<HomeItem>()
            var currentHeader: HomeItem.Header? = null
            var headerAdded = false

            fullList.forEach { item ->
                when (item) {
                    is HomeItem.Header -> {
                        currentHeader = item
                        headerAdded = false
                    }
                    is HomeItem.Card -> {
                        if (item.cardItem.title.contains(query, ignoreCase = true)) {
                            if (currentHeader != null && !headerAdded) {
                                filtered.add(currentHeader!!)
                                headerAdded = true
                            }
                            filtered.add(item)
                        }
                    }
                }
            }

            items = filtered
        }

        notifyDataSetChanged()
    }

}
