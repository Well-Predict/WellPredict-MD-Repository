package com.bangkit.wellpredict.data.api.retrofit

import com.bangkit.wellpredict.data.api.response.AuthResponse
import com.bangkit.wellpredict.data.api.response.DiagnoseHistoriesResponse
import com.bangkit.wellpredict.data.api.response.DiagnoseResponse
import com.bangkit.wellpredict.data.api.response.SymptomsResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface WellPredictApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): AuthResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): AuthResponse

    @POST("logout")
    suspend fun logout() : AuthResponse

    @POST("token")
    suspend fun refreshToken() : AuthResponse

    @GET("symptoms")
    suspend fun getSymptomsList(): SymptomsResponse

    @FormUrlEncoded
    @POST("predict")
    suspend fun diagnose(
        @Field("symptoms") symptoms: Array<String>
    ) : DiagnoseResponse

    @GET("getHistories")
    suspend fun getHistories(): DiagnoseHistoriesResponse
}