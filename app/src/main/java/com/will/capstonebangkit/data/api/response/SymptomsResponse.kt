package com.will.capstonebangkit.data.api.response

import com.google.gson.annotations.SerializedName

data class SymptomsResponse(

	@field:SerializedName("symptoms")
	val symptoms: List<SymptomsItem?>? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class SymptomsItem(

	@field:SerializedName("symptom")
	val symptom: String,

	@field:SerializedName("id")
	val id: String
)
