package com.bangkit.wellpredict.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkit.wellpredict.R
import com.bangkit.wellpredict.data.api.retrofit.ApiConfig
import com.bangkit.wellpredict.data.pref.UserPreference
import com.bangkit.wellpredict.data.pref.dataStore
import com.bangkit.wellpredict.databinding.ActivityMainBinding
import com.bangkit.wellpredict.utils.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tokenManager: TokenManager
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            userPreference = UserPreference.getInstance(dataStore)
            val refreshToken = userPreference.getSession().first().refreshToken

            val wellPredictApiService = ApiConfig.getWellPredictApiService(refreshToken)

            tokenManager = TokenManager(wellPredictApiService, userPreference)

            tokenManager.startTokenRefreshCoroutine()

            setupNavigation()
            setupViews()
        }
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_diagnose, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }

    private fun setupViews() {
        supportActionBar!!.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop token refresh coroutine
        tokenManager.stopTokenRefreshCoroutine()
    }
}