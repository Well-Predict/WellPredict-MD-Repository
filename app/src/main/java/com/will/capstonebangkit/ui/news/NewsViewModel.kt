package com.will.capstonebangkit.ui.news

import androidx.lifecycle.ViewModel
import com.will.capstonebangkit.data.repository.NewsRepository

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    fun getNewsList() = newsRepository.getFilteredNewsList()
}