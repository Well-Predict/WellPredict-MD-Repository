package com.will.capstonebangkit.data.api.retrofit

import com.will.capstonebangkit.data.api.response.SymptomsResponse
import retrofit2.http.GET

interface WellPredictApiService {

    @GET("symptoms")
    suspend fun getSymptomsList(): SymptomsResponse

}