package com.bangkit.wellpredict.ui.news

import androidx.lifecycle.ViewModel
import com.bangkit.wellpredict.data.repository.NewsRepository

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    fun getNewsList() = newsRepository.getNewsList()
}