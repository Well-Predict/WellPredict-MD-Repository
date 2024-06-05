package com.will.capstonebangkit.data.repository

import androidx.lifecycle.liveData
import com.will.capstonebangkit.data.ResultState
import com.will.capstonebangkit.data.api.retrofit.NewsApiService
import kotlinx.coroutines.Dispatchers

class NewsRepository(private val newsApiService: NewsApiService) {

    fun getNewsList() = liveData(Dispatchers.IO) {
        emit(ResultState.Loading)
        try {
            val successResponse = newsApiService.getNewsList()
            val filteredArticles = successResponse.articles?.filter { it?.title != "removed" && it?.urlToImage != null }
            if (filteredArticles != null) {
                emit(ResultState.Success(filteredArticles))
            } else {
                emit(ResultState.Error("No articles found"))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage ?: "Unknown Error"))
        }
    }

    fun getNewsFirst() = liveData(Dispatchers.IO) {
        emit(ResultState.Loading)
        try {
            val successResponse = newsApiService.getNewsList()
            val firstArticle = successResponse.articles?.firstOrNull()
            if (firstArticle != null) {
                emit(ResultState.Success(firstArticle))
            } else {
                emit(ResultState.Error("No articles found"))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage ?: "Unknown Error"))
        }
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            newsApiService: NewsApiService
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(newsApiService)
            }.also { instance = it }
    }
}