package com.will.capstonebangkit.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.will.capstonebangkit.data.repository.NewsRepository

class HomeViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getNewsFirst() = newsRepository.getNewsFirst()
}