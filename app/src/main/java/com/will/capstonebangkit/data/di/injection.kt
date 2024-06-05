package com.will.capstonebangkit.data.di

import android.content.Context
import com.will.capstonebangkit.data.repository.NewsRepository

object Injection {
    fun provideNewsRepository(context: Context): NewsRepository {
        val newsApiService = ApiConfig.getNewsApiService("4d92751e472941548877b4cede1e2a78")
        return NewsRepository.getInstance(newsApiService)
    }
}