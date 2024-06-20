package com.bangkit.wellpredict.data.api.response

import com.google.gson.annotations.SerializedName

data class DiagnoseResponse(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: DiagnoseData? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DiagnoseData(

	@field:SerializedName("result")
	val result: Result? = null
)

data class Result(

	@field:SerializedName("treatment")
	val treatment: String? = null,

	@field:SerializedName("disease")
	val disease: String? = null,

	@field:SerializedName("causes")
	val causes: String? = null,

	@field:SerializedName("description")
	val description: String? = null
)
