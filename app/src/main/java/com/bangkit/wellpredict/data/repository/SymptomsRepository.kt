package com.bangkit.wellpredict.data.repository

import SymptomPreference
import android.util.Log
import androidx.datastore.core.IOException
import androidx.lifecycle.liveData
import com.bangkit.wellpredict.data.ResultState
import com.bangkit.wellpredict.data.api.response.ErrorResponse
import com.bangkit.wellpredict.data.api.retrofit.WellPredictApiService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import retrofit2.HttpException

class SymptomsRepository(
    private val wellPredictApiService: WellPredictApiService,
    private val symptomPreference: SymptomPreference
) {

    fun getSymptomsList() = liveData(Dispatchers.IO) {
        emit(ResultState.Loading)
        try {
            val successResponse = wellPredictApiService.getSymptomsList()
            emit(ResultState.Success(successResponse.symptoms))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            Log.d(TAG, "Http Exception: $errorResponse")
            emit(ResultState.Error(errorResponse.errors?.message ?: "Failed to Fetching Data From Server"))
        }
        catch (e: Exception) {
            Log.d(TAG, "Exception: $e")
            emit(ResultState.Error(e.localizedMessage ?: "Failed to communicate with server"))
        }
    }

    fun getSymptomsFlow(): Flow<List<String>> {
        return symptomPreference.symptomsFlow
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyList<String>()) // Handle error case, emit default value
                } else {
                    throw exception
                }
            }
    }

    suspend fun removeSelectedSymptom(symptom: String) {
        symptomPreference.removeSelectedSymptom(symptom)
    }

    suspend fun clearSelectedSymptoms() {
        symptomPreference.clearSelectedSymptoms()
    }

    companion object {
        private const val TAG = "SymptomsRepository"
        @Volatile
        private var instance: SymptomsRepository? = null
        fun getInstance(
            wellPredictApiService: WellPredictApiService,
            symptomPreference: SymptomPreference
        ): SymptomsRepository =
            instance ?: synchronized(this) {
                instance ?: SymptomsRepository(wellPredictApiService, symptomPreference)
            }.also { instance = it }
    }
}