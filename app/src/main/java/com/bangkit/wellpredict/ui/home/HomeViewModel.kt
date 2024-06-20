package com.bangkit.wellpredict.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.wellpredict.data.model.User
import com.bangkit.wellpredict.data.repository.DiagnoseRepository
import com.bangkit.wellpredict.data.repository.NewsRepository
import com.bangkit.wellpredict.data.repository.UserRepository

class HomeViewModel(private val newsRepository: NewsRepository, private val userRepository: UserRepository, private val diagnoseRepository: DiagnoseRepository) : ViewModel() {
    fun getSession(): LiveData<User> {
        return userRepository.getSession().asLiveData()
    }

    fun getNewsFirst() = newsRepository.getNewsFirst()

    fun getHistoryFirst() = diagnoseRepository.getFirstHistory()
}