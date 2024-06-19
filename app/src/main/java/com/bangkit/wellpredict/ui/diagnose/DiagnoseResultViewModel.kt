package com.bangkit.wellpredict.ui.diagnose

import androidx.lifecycle.ViewModel
import com.bangkit.wellpredict.data.repository.DiagnoseRepository

class DiagnoseResultViewModel(private val diagnoseRepository: DiagnoseRepository) : ViewModel() {
    fun diagnose(symptom: Array<String>) = diagnoseRepository.diagnose(symptom)
}