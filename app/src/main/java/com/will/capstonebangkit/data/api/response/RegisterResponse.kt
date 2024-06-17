package com.will.capstonebangkit.data.api.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
	@field:SerializedName("code")
	val code: String,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("status")
	val status: String
)

data class Data(
	@field:SerializedName("message")
	val message: String
)
