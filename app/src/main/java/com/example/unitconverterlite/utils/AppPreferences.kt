package com.example.unitconverterlite.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "app_prefs")

class AppPreferences(private val context: Context) {

    companion object {
        val AUTO_SAVE_KEY = booleanPreferencesKey("auto_save")
        val DECIMAL_PRECISION_KEY = intPreferencesKey("decimal_precision") // 0,2,4

    }
    suspend fun saveAutoSave(isEnabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[AUTO_SAVE_KEY] = isEnabled
        }
    }

    val isAutoSaveEnabled: Flow<Boolean> = context.dataStore.data
        .map { prefs ->
            prefs[AUTO_SAVE_KEY] ?: true // default ON
        }



    suspend fun saveDecimalPrecision(value: Int) {
        context.dataStore.edit { prefs ->
            prefs[DECIMAL_PRECISION_KEY] = value
        }
    }

    val decimalPrecisionFlow: Flow<Int> = context.dataStore.data
        .map { it[DECIMAL_PRECISION_KEY] ?: 2 }
}
