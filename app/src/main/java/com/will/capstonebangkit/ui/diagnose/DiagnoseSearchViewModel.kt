package com.will.capstonebangkit.ui.diagnose

import androidx.lifecycle.ViewModel
import com.will.capstonebangkit.data.repository.SymptomsRepository

class DiagnoseSearchViewModel(private val symptomsRepository: SymptomsRepository) : ViewModel() {

    fun getSymptomsList() = symptomsRepository.getSymptomsList()
}