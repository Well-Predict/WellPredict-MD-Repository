package com.bangkit.wellpredict.data.api.retrofit

import com.bangkit.wellpredict.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    fun getNewsApiService(token: String): NewsApiService {
        return createApiService(token, BuildConfig.NEWS_BASE_URL, NewsApiService::class.java)
    }

    fun getWellPredictApiService(token: String): WellPredictApiService {
        return createApiService(token, BuildConfig.WELL_PREDICT_BASE_URL, WellPredictApiService::class.java)
    }

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
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(serviceClass)
    }
}