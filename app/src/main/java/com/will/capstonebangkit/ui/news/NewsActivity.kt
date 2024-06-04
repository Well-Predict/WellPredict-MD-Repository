package com.will.capstonebangkit.ui.news

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.will.capstonebangkit.data.ResultState
import com.will.capstonebangkit.databinding.ActivityNewsBinding
import com.will.capstonebangkit.ui.ViewModelFactory


class NewsActivity : AppCompatActivity() {

    private val viewModel by viewModels<NewsViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvNews.layoutManager = layoutManager
        val adapter = NewsAdapter()
        viewModel.getNewsList().observe(this) { result ->
            if (result != null){
                when (result) {
                    is ResultState.Loading -> {
                        lottieLoadingAnimation(true)
                    }
                    is ResultState.Success -> {
                        lottieLoadingAnimation(false)
                        adapter.submitList(result.data)
                    }
                    is ResultState.Error -> {
                        lottieLoadingAnimation(false)
//                        showAlert(result.error,)
                    }
                }
            }
        }
        binding.rvNews.adapter = adapter
    }

    private fun lottieLoadingAnimation(isLoading: Boolean){
        if (isLoading) {
            binding.lottieLoadingAnimation.playAnimation();
        } else {
            binding.lottieLoadingAnimation.setVisibility(View.GONE);
        }
    }

    fun onBackButtonClick(view: View) {
        finish();
    }
}