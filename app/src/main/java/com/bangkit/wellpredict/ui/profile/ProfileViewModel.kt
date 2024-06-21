package com.bangkit.wellpredict.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.wellpredict.data.model.User
import com.bangkit.wellpredict.data.repository.UserRepository

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun logout() = userRepository.logout()

    fun getSession(): LiveData<User> {
        return userRepository.getSession().asLiveData()
    }
}