package com.bangkit.wellpredict.data.api.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("accesToken")
	val accesToken: String? = null,

	@field:SerializedName("refreshToken")
	val refreshToken: String? = null
)
