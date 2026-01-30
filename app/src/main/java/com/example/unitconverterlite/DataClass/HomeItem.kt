package com.example.unitconverterlite.DataClass

sealed class HomeItem {
    data class Header(val title: String) : HomeItem()
    data class Card(val cardItem: CardItem) : HomeItem()
}