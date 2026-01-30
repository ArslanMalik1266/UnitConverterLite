package com.example.unitconverterlite.Adaptor

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.unitconverterlite.fragments.PercentageAddFragment
import com.example.unitconverterlite.fragments.PercentageOfFragment
import com.example.unitconverterlite.fragments.PercentageSubtractFragment

class PercentagePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragments = listOf(
        PercentageOfFragment(),
        PercentageAddFragment(),
        PercentageSubtractFragment()
    )

    override fun getItemCount() = fragments.size
    override fun createFragment(position: Int) = fragments[position]
}
