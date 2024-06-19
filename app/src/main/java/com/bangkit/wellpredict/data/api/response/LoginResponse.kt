package com.bangkit.wellpredict.data.api.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("code")
    val code: String,

    @field:SerializedName("data")
    val data: DataSuccess,

    @field:SerializedName("status")
    val status: String
)


data class DataSuccess(

    @field:SerializedName("accesToken")
    val accesToken: String,

    @field:SerializedName("refreshToken")
    val refreshToken: String
)

