package com.bangkit.wellpredict.data.repository

import SymptomPreference
import android.util.Log
import androidx.lifecycle.liveData
import com.bangkit.wellpredict.data.ResultState
import com.bangkit.wellpredict.data.api.response.ErrorResponse
import com.bangkit.wellpredict.data.api.retrofit.WellPredictApiService
import com.bangkit.wellpredict.data.model.User
import com.bangkit.wellpredict.data.pref.UserPreference
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class UserRepository(
    private val wellPredictApiService: WellPredictApiService,
    private val userPreference: UserPreference
) {

    suspend fun saveSession(user: User) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<User> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun register(name : String, email: String, password: String) = liveData(Dispatchers.IO) {
        emit(ResultState.Loading)

        try {
            val successResponse = wellPredictApiService.register(name, email, password)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            Log.d(TAG, "Register error: $errorResponse")
            emit(ResultState.Error(errorResponse.errors?.message ?: "Server Error 404"))
        } catch (e: Exception) {
            Log.e(TAG, "Register exception: ${e.message}", e)
            emit(ResultState.Error("Failed to communicate with server"))
        }
    }

    fun login(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(ResultState.Loading)

        try {
            val successResponse = wellPredictApiService.login(email, password)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            Log.d(TAG, "Login error: $errorResponse")
            emit(ResultState.Error(errorResponse.errors?.message ?: "Server Error 404"))
        } catch (e: Exception) {
            Log.e(TAG, "Login exception: ${e.message}", e)
            emit(ResultState.Error("Failed to communicate with server"))
        }
    }
    
    companion object {
        private const val TAG = "UserRepository"
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            wellPredictApiService: WellPredictApiService,
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(wellPredictApiService, userPreference)
            }.also { instance = it }
    }
}