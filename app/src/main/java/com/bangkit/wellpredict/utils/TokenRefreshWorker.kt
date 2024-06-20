import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bangkit.wellpredict.data.api.response.ErrorResponse
import com.bangkit.wellpredict.data.api.retrofit.ApiConfig
import com.bangkit.wellpredict.data.pref.UserPreference
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

class TokenRefreshWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private lateinit var userPreference: UserPreference
    override fun doWork(): Result {
        return runBlocking {
            try {
                refreshAccessToken()
                Result.success()
            } catch (e: Exception) {
                Log.e("TokenRefreshWorker", "Failed to refresh token: ${e.message}")
                Result.retry() // Retry jika terjadi kesalahan
            }
        }
    }

    private suspend fun refreshAccessToken() {
        userPreference = UserPreference.getInstance(applicationContext.dataStore)

        val refreshToken = userPreference.getSession().first().refreshToken
        val oldAccessToken = userPreference.getSession().first().accessToken

        Log.d(TAG, "oldAccessToken: $oldAccessToken ")

        try {
            val response = ApiConfig.getWellPredictApiService(refreshToken).refreshToken()

            if (response.code?.toInt() == 200) {
                response.data?.let { tokenResponse ->
                    // Update AccessToken in UserPreference
                    Log.d(TAG, "newAccessToken: ${tokenResponse.accesToken}")
                    tokenResponse.accesToken?.let {
                        UserPreference.getInstance(applicationContext.dataStore).updateAccessToken(it)
                    }
                }
            }
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
            Log.d(TAG, "Login error: $errorResponse")
        } catch (e: Exception) {
            Log.e(TAG, "Login exception: ${e.message}", e)
        }
    }

    companion object {
        const val TAG = "TokenRefreshWorker"
    }
}
