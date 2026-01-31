package com.example.unitconverterlite.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unitconverterlite.DataClass.HistoryDatabase
import com.example.unitconverterlite.DataClass.HistoryItem
import com.example.unitconverterlite.DataClass.HistoryRepository
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HistoryRepository

    init {
        val dao = HistoryDatabase.getDatabase(application).historyDao()
        repository = HistoryRepository(dao)
    }

    val history: LiveData<List<HistoryItem>> = repository.allHistory

    fun addHistory(item: HistoryItem) {
        viewModelScope.launch {
            repository.insert(item)
        }
    }
}


