import com.will.capstonebangkit.BuildConfig
import com.will.capstonebangkit.data.api.retrofit.NewsApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getNewsApiService(token: String): NewsApiService {
        return createApiService(token, BuildConfig.NEWS_BASE_URL, NewsApiService::class.java)
    }

//    fun getSecondApiService(token: String): SecondApiService {
//        return createApiService(token, "https://second-api-url.com/", SecondApiService::class.java)
//    }

    private fun <T> createApiService(token: String, baseUrl: String, serviceClass: Class<T>): T {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(requestHeaders)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(serviceClass)
    }
}