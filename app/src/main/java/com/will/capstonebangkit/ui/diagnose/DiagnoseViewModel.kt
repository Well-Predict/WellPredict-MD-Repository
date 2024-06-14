package com.will.capstonebangkit.ui.diagnose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.will.capstonebangkit.data.repository.SymptomsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class DiagnoseViewModel(private val symptomsRepository: SymptomsRepository) : ViewModel() {

    val selectedSymptom: Flow<List<String>> = symptomsRepository.getSymptomsFlow()

    fun removeSelectedSymptom(symptom: String) {
        viewModelScope.launch {
            symptomsRepository.removeSelectedSymptom(symptom)
        }
    }

    fun clearSelectedSymptoms() {
        viewModelScope.launch {
            symptomsRepository.clearSelectedSymptoms()
        }
    }
}