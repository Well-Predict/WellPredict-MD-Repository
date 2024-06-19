package com.bangkit.wellpredict.data.repository

import androidx.lifecycle.liveData
import com.bangkit.wellpredict.data.ResultState
import com.bangkit.wellpredict.data.api.retrofit.NewsApiService
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
            val articles = successResponse.articles ?: emptyList()
            val validArticles = articles.filter { it?.title != "removed" && it?.urlToImage != null }

            val firstValidArticle = validArticles.firstOrNull()
            if (firstValidArticle != null) {
                emit(ResultState.Success(firstValidArticle))
            } else {
                emit(ResultState.Error("No valid articles found"))
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