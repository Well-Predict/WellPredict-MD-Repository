package com.bangkit.wellpredict.ui.auth

import androidx.lifecycle.ViewModel
import com.bangkit.wellpredict.data.repository.UserRepository


class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun register(name: String, email: String, password: String) = userRepository.register(name, email, password)
}