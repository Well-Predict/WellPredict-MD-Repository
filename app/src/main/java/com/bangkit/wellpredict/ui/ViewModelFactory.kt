package com.bangkit.wellpredict.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.wellpredict.data.di.Injection
import com.bangkit.wellpredict.data.repository.NewsRepository
import com.bangkit.wellpredict.data.repository.SymptomsRepository
import com.bangkit.wellpredict.ui.diagnose.DiagnoseSearchViewModel
import com.bangkit.wellpredict.ui.diagnose.DiagnoseViewModel
import com.bangkit.wellpredict.ui.home.HomeViewModel
import com.bangkit.wellpredict.ui.news.NewsViewModel

class ViewModelFactory(private val newsRepository: NewsRepository, private val symptomsRepository: SymptomsRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                NewsViewModel(newsRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(newsRepository) as T
            }
            modelClass.isAssignableFrom(DiagnoseViewModel::class.java) -> {
                DiagnoseViewModel(symptomsRepository) as T
            }
            modelClass.isAssignableFrom(DiagnoseSearchViewModel::class.java) -> {
                DiagnoseSearchViewModel(symptomsRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(
                        Injection.provideNewsRepository(context),
                        Injection.provideSymptomsRepository(context)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}