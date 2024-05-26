package org.moneymanager.com.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.moneymanager.com.utils.Constants.EMPTY_STRING
import javax.inject.Singleton

val Context.themePrefDataStore by preferencesDataStore("data_store_pref")

class DataStoreManager(context: Context) : DataStoreImpl {

    private val dataStore = context.themePrefDataStore

    // used to get the data from datastore
    override val uiMode: Flow<String>
        get() = dataStore.data.map { preferences ->
//            val uiMode = preferences[UI_MODE_KEY] ?: false
//            uiMode
            val pwd = preferences[KEY_LOCK_PWD] ?: EMPTY_STRING
            pwd
        }

    // used to save the ui preference to datastore
    override suspend fun saveToDataStore(pwd: String) {
        dataStore.edit { preferences ->
            preferences[KEY_LOCK_PWD] = pwd
        }
    }

    companion object {
        private val KEY_LOCK_PWD = stringPreferencesKey("lock_pwd")
    }
}

@Singleton
interface DataStoreImpl {

    val uiMode: Flow<String>

    suspend fun saveToDataStore(pwd: String)
}
