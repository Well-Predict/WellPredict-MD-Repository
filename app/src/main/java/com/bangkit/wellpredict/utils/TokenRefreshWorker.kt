package com.bangkit.wellpredict.utils

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bangkit.wellpredict.data.api.response.ErrorResponse
import com.bangkit.wellpredict.data.api.retrofit.ApiConfig
import com.bangkit.wellpredict.data.pref.UserPreference
import com.google.gson.Gson
import dataStore
import kotlinx.coroutines.flow.first
import retrofit2.HttpException

class TokenRefreshWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        val userPreference = UserPreference.getInstance(applicationContext.dataStore)
        val refreshToken = userPreference.getSession().first().refreshToken
        val oldAccessToken = userPreference.getSession().first().accessToken

        try {
            val response = ApiConfig.getWellPredictApiService(refreshToken).refreshAccessToken()

            if (response.code?.toInt() == 200) {
                response.data?.let { tokenResponse ->
                    // Update AccessToken in UserPreference
                    Log.d(TAG, "oldAccessToken: $oldAccessToken")
                    Log.d(TAG, "newAccessToken: ${tokenResponse.accesToken}")
                    tokenResponse.accesToken?.let {
                        userPreference.updateAccessToken(it)
                    }
                    Log.d(TAG, "Refresh Token: $tokenResponse")
                }
                return Result.success()
            }

            return Result.retry()
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            Log.d(TAG, "Token refresh error: $errorResponse")
            return Result.retry()
        } catch (e: Exception) {
            Log.e(TAG, "Token refresh exception: ${e.message}", e)
            return Result.failure()
        }
    }

    companion object {
        const val TAG = "TokenRefreshWorker"
    }
}
