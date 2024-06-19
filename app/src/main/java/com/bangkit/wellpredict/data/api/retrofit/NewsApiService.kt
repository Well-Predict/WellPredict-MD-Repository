package com.bangkit.wellpredict.data.api.retrofit

import com.bangkit.wellpredict.data.api.response.NewsResponse
import retrofit2.http.GET

interface NewsApiService {
    @GET("top-headlines?country=us&category=health")
    suspend fun getNewsList(): NewsResponse
}