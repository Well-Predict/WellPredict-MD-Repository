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
        val symptomsApiService = ApiConfig.getWellPredictApiService("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImJvdDFAZ21haWwuY29tIiwibmFtZSI6ImJvdCIsInBhc3N3b3JkIjoiJDJiJDEwJDJvejZ4RWRNcld6aU9TTnlzdWQubk9nT2lyOEZBL0ppUjFnQ3N5WG9uYU8vRWtKMFlvamN1IiwiaWF0IjoxNzE4NjU4NjU4LCJleHAiOjE3MTg2NjIyNTh9.w1S7sLc-CKfElLFURniEltD4gTisk1GASaLp7v9C5Ag")
        val symptomPref = SymptomPreference.getInstance(context)
        return SymptomsRepository.getInstance(symptomsApiService, symptomPref)
    }
}