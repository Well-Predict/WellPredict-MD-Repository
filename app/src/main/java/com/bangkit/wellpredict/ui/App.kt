package com.bangkit.wellpredict.ui

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bangkit.wellpredict.utils.TokenRefreshWorker
import java.util.concurrent.TimeUnit

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        setupTokenRefreshWorker()
    }

    private fun setupTokenRefreshWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val tokenRefreshWorkRequest = PeriodicWorkRequestBuilder<TokenRefreshWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            TokenRefreshWorker.TAG,
            ExistingPeriodicWorkPolicy.UPDATE,
            tokenRefreshWorkRequest
        )
    }
}