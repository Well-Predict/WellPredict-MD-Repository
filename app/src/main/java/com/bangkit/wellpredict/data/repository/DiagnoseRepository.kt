package com.bangkit.wellpredict.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.bangkit.wellpredict.data.ResultState
import com.bangkit.wellpredict.data.api.response.ErrorResponse
import com.bangkit.wellpredict.data.api.retrofit.WellPredictApiService
import com.bangkit.wellpredict.data.pref.UserPreference
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException

class DiagnoseRepository(
    private val wellPredictApiService: WellPredictApiService,
    private val userPreference: UserPreference
) {

    fun diagnose(symptom: Array<String>) = liveData(Dispatchers.IO) {
        emit(ResultState.Loading)

        try {
            val successResponse = wellPredictApiService.diagnose(symptom)
            emit(ResultState.Success(successResponse.data?.result))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            Log.d(TAG, "Diagnose error: $errorResponse")
            emit(ResultState.Error(errorResponse.errors?.message ?: "Server Error 404"))
        } catch (e: Exception) {
            Log.e(TAG, "Diagnose exception: ${e.message}", e)
            emit(ResultState.Error("Failed to communicate with server"))
        }
    }

    companion object {
        private const val TAG = "DiagnoseRepository"
        @Volatile
        private var instance: DiagnoseRepository? = null
        fun getInstance(
            wellPredictApiService: WellPredictApiService,
            userPreference: UserPreference
        ): DiagnoseRepository =
            instance ?: synchronized(this) {
                instance ?: DiagnoseRepository(wellPredictApiService, userPreference)
            }.also { instance = it }
    }
}