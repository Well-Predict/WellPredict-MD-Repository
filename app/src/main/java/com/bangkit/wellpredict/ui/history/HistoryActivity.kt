package com.bangkit.wellpredict.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.wellpredict.data.ResultState
import com.bangkit.wellpredict.data.api.response.HistoryItem
import com.bangkit.wellpredict.databinding.ActivityHistoryBinding
import com.bangkit.wellpredict.ui.ViewModelFactory
import com.bangkit.wellpredict.ui.adapter.HistoryAdapter

class HistoryActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHistoryBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<HistoryViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        supportActionBar?.hide()

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvHistory.layoutManager = layoutManager
        val adapter = HistoryAdapter()

        // Set listener untuk menangkap klik item pada adapter
        adapter.setOnItemClickListener { historyItem ->
            // Buat Intent untuk Activity tujuan (misalnya DetailActivity)
            val intent = Intent(this, HistoryDetailActivity::class.java)

            // Kirim data ArrayList<HistoryItem> menggunakan putParcelableArrayListExtra
            val historyItems = ArrayList<HistoryItem>()
            historyItems.add(historyItem)
            intent.putParcelableArrayListExtra("HISTORY_ITEMS", historyItems)
            startActivity(intent)
        }

        viewModel.getHistoryList().observe(this) { result ->
            if (result != null){
                when (result) {
                    is ResultState.Loading -> {
                        lottieLoadingAnimation(true)
                    }
                    is ResultState.Success -> {
                        lottieLoadingAnimation(false)
                        adapter.submitList(result.data.historyItem)
                    }
                    is ResultState.Error -> {
                        lottieLoadingAnimation(false)
                    }
                }
            }
        }
        binding.rvHistory.adapter = adapter
    }

    private fun lottieLoadingAnimation(isLoading: Boolean){
        if (isLoading) {
            binding.lottieLoadingAnimation.playAnimation()
        } else {
            binding.lottieLoadingAnimation.setVisibility(View.GONE)
        }
    }

    fun onBackButtonClick(view: View) {
        finish()
    }
}