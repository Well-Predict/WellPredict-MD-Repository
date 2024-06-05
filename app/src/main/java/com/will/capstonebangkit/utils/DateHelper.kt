package com.will.capstonebangkit.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class DateHelper {

//    // Format waktu dari API
//    val apiTimeString = "2024-06-04T00:14:00Z"
//
//    // Zona waktu Indonesia
//    @RequiresApi(Build.VERSION_CODES.O)
//    val zoneId = ZoneId.of("Asia/Jakarta")
//
//    // Parsing waktu dari string ISO 8601
//    @RequiresApi(Build.VERSION_CODES.O)
//    val postingTime = Instant.parse(apiTimeString)
//
//    // Mengonversi waktu ke zona waktu Indonesia
//    val postingTimeInIndonesia = LocalDateTime.ofInstant(postingTime, zoneId)
//    println("Waktu posting di Indonesia: $postingTimeInIndonesia")
//
//    // Mendapatkan waktu saat ini di zona waktu Indonesia
//    val currentTimeInIndonesia = LocalDateTime.now(zoneId)
//    println("Waktu saat ini di Indonesia: $currentTimeInIndonesia")
//
//    // Menghitung selisih waktu
//    val duration = Duration.between(postingTimeInIndonesia, currentTimeInIndonesia)
//    val hours = duration.toHours()
//    val minutes = duration.toMinutes() % 60
//    val seconds = duration.seconds % 60
//
//    println("Selisih waktu: ${hours} jam, ${minutes} menit, ${seconds} detik")
}