package com.bangkit.wellpredict.data.api.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("errors")
	val errors: Errors? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("message")
		val message: String? = null
	)

data class Errors(

	@field:SerializedName("message")
	val message: String? = null
)
