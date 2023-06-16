package com.muslim.simplenotesapp.data.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("data_store")

class DataStoreManager(val context: Context) {

    suspend fun saveNumber(secretNumberData: SecretNumberData) {
        context.dataStore.edit { pref ->
            pref[intPreferencesKey("secret_number")] = secretNumberData.number
        }
    }

    fun getNumber() = context.dataStore.data.map { pref ->
        return@map pref[intPreferencesKey("secret_number")] ?: 0
    }
}