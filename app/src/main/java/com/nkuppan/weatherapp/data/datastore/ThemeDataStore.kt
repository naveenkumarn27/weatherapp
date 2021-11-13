package com.nkuppan.weatherapp.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.map

class ThemeDataStore(private val dataStore: DataStore<Preferences>) {

    suspend fun setMode(mode: Int) = dataStore.edit { preferences ->
        preferences[KEY_MODE] = mode
    }

    fun getMode(defaultMode: Int) = dataStore.data.map { preferences ->
        preferences[KEY_MODE] ?: defaultMode
    }

    companion object {
        private val KEY_MODE = intPreferencesKey("mode")
    }
}
