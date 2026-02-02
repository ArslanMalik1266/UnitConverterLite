package com.example.unitconverterlite.DataClass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class HistoryItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val valueGiven: String,
    val valueGivenUnit: String,
    val valueReceived: String,
    val valueReceivedUnit: String,
    val unitName: String,
    val ratioX: String? = null,
    val ratioY: String? = null,
    val bmiHeight: String? = null,
    val bmiWeight: String? = null
)

