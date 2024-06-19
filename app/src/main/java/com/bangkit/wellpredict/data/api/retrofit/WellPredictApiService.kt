package com.bangkit.wellpredict.data.api.retrofit

import com.bangkit.wellpredict.data.api.response.SymptomsResponse
import retrofit2.http.GET

interface WellPredictApiService {

    @GET("symptoms")
    suspend fun getSymptomsList(): SymptomsResponse

}