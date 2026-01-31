package com.example.unitconverterlite.DataClass

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class HistoryRepository(private val dao: HistoryDao) {

    val allHistory: LiveData<List<HistoryItem>> = dao.getAllHistory()

    suspend fun insert(historyItem: HistoryItem) {
        dao.insert(historyItem)
    }

    suspend fun clearHistory() {
        dao.clearHistory()
    }
}
