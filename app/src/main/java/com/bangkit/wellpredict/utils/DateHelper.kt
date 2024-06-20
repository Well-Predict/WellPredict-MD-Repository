package com.bangkit.wellpredict.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import kotlin.math.abs

class DateHelper {
    companion object{
        fun convertTime(uploadTime: String): String {
            val givenDateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

            try {
                // Parse string ke objek Date
                val givenDateTime = givenDateTimeFormat.parse(uploadTime)

                // Mendapatkan waktu saat ini
                val currentTime = System.currentTimeMillis()

                // Menghitung selisih waktu
                val timeDifferenceInMillis = abs(currentTime - givenDateTime!!.time)
                val timeDifferenceInDays = timeDifferenceInMillis / (1000 * 60 * 60 * 24)

                // Menampilkan selisih waktu dalam format yang sesuai
                val formattedDifference = when {
                    timeDifferenceInDays < 1 -> {
                        val hours = (timeDifferenceInMillis / (1000 * 60 * 60)) % 24
                        "$hours hours ago"
                    }

                    timeDifferenceInDays == 1L -> "1 day ago"
                    else -> "$timeDifferenceInDays days ago"
                }

                return formattedDifference
            } catch (e: Exception) {
                return "Failed to convert date and time: ${e.message}"
            }
        }

        fun formatDateToLocal(dateStr: String): String {
            try {
                // Parsing string tanggal dari format ISO 8601
                val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                utcFormat.timeZone = TimeZone.getTimeZone("UTC")
                val date = utcFormat.parse(dateStr)

                // Mengonversi ke waktu lokal perangkat
                val localFormat = SimpleDateFormat("d MMMM yyyy, h.mma", Locale.getDefault())
                localFormat.timeZone = TimeZone.getDefault()

                return localFormat.format(date!!)
            } catch (e: Exception) {
                e.printStackTrace()
                return "Invalid Date"
            }
        }
    }
}