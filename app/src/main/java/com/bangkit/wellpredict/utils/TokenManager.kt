package com.bangkit.wellpredict.utils

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.bangkit.wellpredict.data.pref.UserPreference
import com.bangkit.wellpredict.data.api.retrofit.WellPredictApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TokenManager(private val wellPredictApiService: WellPredictApiService, private val userPreference: UserPreference) {
    private val handler = Handler(Looper.getMainLooper())
    private var tokenRefreshRunnable: Runnable? = null

    fun startTokenRefreshCoroutine() {
        // Cancel existing runnable if any
        handler.removeCallbacksAndMessages(null)

        // Schedule token refresh every REFRESH_INTERVAL_MINUTES
        tokenRefreshRunnable = Runnable {
            CoroutineScope(Dispatchers.IO).launch {
                refreshToken()
            }
            // Reschedule the refresh
            handler.postDelayed(tokenRefreshRunnable!!, TimeUnit.MINUTES.toMillis(REFRESH_INTERVAL_MINUTES))
        }

        // Start the first token refresh immediately
        handler.post(tokenRefreshRunnable!!)
    }

    fun stopTokenRefreshCoroutine() {
        // Stop token refresh coroutine
        handler.removeCallbacksAndMessages(null)
    }

    private suspend fun refreshToken() {
        try {
            val refreshToken = userPreference.getSession().first().refreshToken
            Log.d(TAG, "Current refreshToken: $refreshToken")
            try {
                val newAccessToken = wellPredictApiService.refreshToken(refreshToken)
                newAccessToken.data?.accesToken?.let { userPreference.updateAccessToken(it) }
                Log.d(TAG, "successRefreshToken : $newAccessToken")
            } catch (e: Exception) {
                Log.d(TAG, "refreshToken: $e")
            }
        } catch (e: Exception) {
            // Handle refresh token failure
             Log.e(TAG, "Failed to refresh token: ${e.message}", e)
        }
    }

    companion object {
        private const val TAG = "TokenManager"
        private const val REFRESH_INTERVAL_MINUTES = 59L
    }
}