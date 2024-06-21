package com.bangkit.wellpredict.ui.diagnose

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.wellpredict.data.model.User
import com.bangkit.wellpredict.data.repository.DiagnoseRepository
import com.bangkit.wellpredict.data.repository.UserRepository

class DiagnoseResultViewModel(private val diagnoseRepository: DiagnoseRepository, private val userRepository: UserRepository) : ViewModel() {
    fun diagnose(symptom: Array<String>) = diagnoseRepository.diagnose(symptom)

    fun getSession(): LiveData<User> {
        return userRepository.getSession().asLiveData()
    }
}