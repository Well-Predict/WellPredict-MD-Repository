package com.bangkit.wellpredict.ui.diagnose

import androidx.lifecycle.ViewModel
import com.bangkit.wellpredict.data.repository.SymptomsRepository

class DiagnoseSearchViewModel(private val symptomsRepository: SymptomsRepository) : ViewModel() {

    fun getSymptomsList() = symptomsRepository.getSymptomsList()
}