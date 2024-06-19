package com.bangkit.wellpredict.ui.home

import androidx.lifecycle.ViewModel
import com.bangkit.wellpredict.data.repository.NewsRepository

class HomeViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getNewsFirst() = newsRepository.getNewsFirst()
}