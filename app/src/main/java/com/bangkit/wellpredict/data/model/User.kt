package com.bangkit.wellpredict.data.model

data class User(
    val name: String,
    val email: String,
    val accessToken: String,
    val refreshToken: String,
    val isLogin: Boolean = false
)
