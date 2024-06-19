package com.bangkit.wellpredict.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.wellpredict.data.model.User
import com.bangkit.wellpredict.data.repository.UserRepository
import kotlinx.coroutines.launch


class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun saveSession(user: User) {
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
    }

    fun login(email: String, password: String) = userRepository.login(email, password)
}