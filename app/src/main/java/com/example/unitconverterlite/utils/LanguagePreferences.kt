package com.example.unitconverterlite.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "language_prefs")

class LanguagePreferences(private val context: Context) {

    companion object {
        private val LANGUAGE_KEY = stringPreferencesKey("app_language")
        const val ENGLISH = "en"
        const val URDU = "ur"
    }

    val languageFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[LANGUAGE_KEY] ?: ENGLISH
    }

    suspend fun saveLanguage(langCode: String) {
        context.dataStore.edit { prefs ->
            prefs[LANGUAGE_KEY] = langCode
        }
    }
}
