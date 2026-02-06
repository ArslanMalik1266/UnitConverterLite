package com.example.unitconverterlite.DataClass

import androidx.fragment.app.Fragment

data class CardItem(
    val title: String,
    val iconRes: Int,
    val fragment: Fragment,
    val keywords: List<String>
)

