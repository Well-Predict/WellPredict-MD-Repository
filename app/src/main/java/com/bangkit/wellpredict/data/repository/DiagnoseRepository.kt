package com.bangkit.wellpredict.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.bangkit.wellpredict.R
import com.bangkit.wellpredict.data.ResultState
import com.bangkit.wellpredict.data.api.response.ErrorResponse
import com.bangkit.wellpredict.data.api.retrofit.WellPredictApiService
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException

class DiagnoseRepository(
    private val wellPredictApiService: WellPredictApiService
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

    fun getHistories() = liveData(Dispatchers.IO){
        emit(ResultState.Loading)
        try {
            val successResponse = wellPredictApiService.getHistories()
            Log.d(TAG, "Get Histories Success: ${successResponse.historyItem}")
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            emit(ResultState.Error(errorResponse?.message ?: R.string.failed_fetching.toString()))
            Log.d(TAG, "Get Histories error: $errorResponse")
        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage ?: R.string.failed_communicate.toString()))
            Log.e(TAG, "Get Histories exception: ${e.message}", e)
        }
    }

    fun getFirstHistory() = liveData(Dispatchers.IO){
        emit(ResultState.Loading)
        try {
            val successResponse = wellPredictApiService.getHistories()
            val firstHistory = successResponse.historyItem?.firstOrNull()
            Log.d(TAG, "getFirstHistory: $firstHistory")
            emit(ResultState.Success(firstHistory))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            emit(ResultState.Error(errorResponse?.message ?: R.string.failed_fetching.toString()))
            Log.d(TAG, "Get Histories error: $errorResponse")
        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage ?: R.string.failed_communicate.toString()))
            Log.e(TAG, "Get Histories exception: ${e.message}", e)
        }
    }

    companion object {
        private const val TAG = "DiagnoseRepository"
        @Volatile
        private var instance: DiagnoseRepository? = null
        fun getInstance(
            wellPredictApiService: WellPredictApiService
        ): DiagnoseRepository =
            instance ?: synchronized(this) {
                instance ?: DiagnoseRepository(wellPredictApiService)
            }.also { instance = it }
    }
}