package com.bangkit.wellpredict.ui.history

import androidx.lifecycle.ViewModel
import com.bangkit.wellpredict.data.repository.DiagnoseRepository

class HistoryViewModel(private val diagnoseRepository: DiagnoseRepository) : ViewModel() {
    fun getHistoryList() = diagnoseRepository.getHistories()
}