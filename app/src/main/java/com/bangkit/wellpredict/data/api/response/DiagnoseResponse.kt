package com.bangkit.wellpredict.data.api.response

import com.google.gson.annotations.SerializedName

data class DiagnoseResponse(
	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val diseaseData: DiseaseData? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DiseaseData(
	@field:SerializedName("result")
	val result: String? = null
)
