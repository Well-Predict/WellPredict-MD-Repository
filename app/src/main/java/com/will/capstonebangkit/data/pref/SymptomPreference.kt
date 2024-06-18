import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "symptom_pref")

class SymptomPreference private constructor(private val dataStore: DataStore<Preferences>) {

    // Flow untuk mengamati perubahan pada data symptoms
    val symptomsFlow: Flow<List<String>> = dataStore.data
        .map { preferences ->
            preferences[SYMPTOMS_KEY]?.split(",") ?: emptyList()
        }

    // Menyimpan daftar symptoms ke DataStore
    suspend fun saveSelectedSymptom(symptom: String) {
        dataStore.edit { preferences ->
            val currentSymptoms = preferences[SYMPTOMS_KEY]?.split(",")?.toMutableList() ?: mutableListOf()
            if (!currentSymptoms.contains(symptom)) {
                currentSymptoms.add(symptom)
                preferences[SYMPTOMS_KEY] = currentSymptoms.joinToString(",")
            }
        }
    }

    suspend fun removeSelectedSymptom(symptom: String) {
        dataStore.edit { preferences ->
            val currentSymptoms = preferences[SYMPTOMS_KEY]?.split(",")?.toMutableList() ?: mutableListOf()
            if (currentSymptoms.remove(symptom)) {
                // Hapus semua gejala jika tidak ada yang tersisa
                if (currentSymptoms.isEmpty()) {
                    preferences.remove(SYMPTOMS_KEY)
                } else {
                    preferences[SYMPTOMS_KEY] = currentSymptoms.joinToString(",")
                }
            }
        }
    }

    // Menghapus data symptoms dari DataStore
    suspend fun clearSelectedSymptoms() {
        dataStore.edit { preferences ->
            preferences.remove(SYMPTOMS_KEY)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SymptomPreference? = null

        private val SYMPTOMS_KEY = stringPreferencesKey("symptoms_list")

        // Mendapatkan instance dari SymptomPrefrence
        fun getInstance(context: Context): SymptomPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = SymptomPreference(context.dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
