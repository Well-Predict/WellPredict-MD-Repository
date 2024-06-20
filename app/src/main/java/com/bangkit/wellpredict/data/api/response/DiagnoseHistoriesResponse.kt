package com.bangkit.wellpredict.data.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class DiagnoseHistoriesResponse(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val historyItem: List<HistoryItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)
@Parcelize
data class HistoryItem(

	@field:SerializedName("symptoms")
	val symptoms: List<String?>? = null,

	@field:SerializedName("disease")
	val disease: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("history_id")
	val historyId: Int? = null
) : Parcelable
