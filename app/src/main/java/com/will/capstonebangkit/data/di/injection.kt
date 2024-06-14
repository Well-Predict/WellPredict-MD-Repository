package com.will.capstonebangkit.data.di

import SymptomPreference
import android.content.Context
import com.will.capstonebangkit.data.api.retrofit.ApiConfig
import com.will.capstonebangkit.data.repository.NewsRepository
import com.will.capstonebangkit.data.repository.SymptomsRepository

object Injection {
    fun provideNewsRepository(context: Context): NewsRepository {
        val newsApiService = ApiConfig.getNewsApiService("4d92751e472941548877b4cede1e2a78")
        return NewsRepository.getInstance(newsApiService)
    }

    fun provideSymptomsRepository(context: Context): SymptomsRepository {
        val symptomsApiService = ApiConfig.getWellPredictApiService("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiYm90IiwiZW1haWwiOiJib3QxQGdtYWlsLmNvbSIsImlhdCI6MTcxODM2NDc3OCwiZXhwIjoxNzE4MzY4Mzc4fQ.9XwEzt7KnsV9TVOIp6YFhYhNM1nJgekYhoP0sPw0IhQ")
        val symptomPref = SymptomPreference.getInstance(context)
        return SymptomsRepository.getInstance(symptomsApiService, symptomPref)
    }
}