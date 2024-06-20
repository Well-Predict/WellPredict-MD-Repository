package com.bangkit.wellpredict

import TokenRefreshWorker
import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        scheduleTokenRefresh()
    }

    private fun scheduleTokenRefresh() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val tokenRefreshRequest = PeriodicWorkRequestBuilder<TokenRefreshWorker>(
            15, TimeUnit.MINUTES // Interval setiap 15 menit
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            TokenRefreshWorker.TAG,
            ExistingPeriodicWorkPolicy.REPLACE, // Ganti jika ada pekerjaan sebelumnya dengan nama yang sama
            tokenRefreshRequest
        )
    }
}