package com.bangkit.wellpredict.data.di

import SymptomPreference
import android.content.Context
import com.bangkit.wellpredict.data.api.retrofit.ApiConfig
import com.bangkit.wellpredict.data.pref.UserPreference
import com.bangkit.wellpredict.data.pref.dataStore
import com.bangkit.wellpredict.data.repository.DiagnoseRepository
import com.bangkit.wellpredict.data.repository.NewsRepository
import com.bangkit.wellpredict.data.repository.SymptomsRepository
import com.bangkit.wellpredict.data.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideNewsRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.getNewsApiService("4d92751e472941548877b4cede1e2a78")
        return NewsRepository.getInstance(apiService)
    }

    fun provideSymptomsRepository(context: Context): SymptomsRepository {
        val userPref = UserPreference.getInstance(context.dataStore)
        val session = runBlocking { userPref.getSession().first() }
        val apiService = ApiConfig.getWellPredictApiService(session.accessToken)
        val symptomPref = SymptomPreference.getInstance(context)
        return SymptomsRepository.getInstance(apiService, symptomPref)
    }

    fun provideUserRepository(context: Context): UserRepository {
        val userPref = UserPreference.getInstance(context.dataStore)
        val session = runBlocking { userPref.getSession().first() }
        val apiService = ApiConfig.getWellPredictApiService(session.accessToken)
        return UserRepository.getInstance(apiService, userPref)
    }

    fun provideDiagnoseRepository(context: Context): DiagnoseRepository {
        val userPref = UserPreference.getInstance(context.dataStore)
        val session = runBlocking { userPref.getSession().first() }
        val apiService = ApiConfig.getWellPredictApiService(session.accessToken)
        return DiagnoseRepository.getInstance(apiService)
    }
}