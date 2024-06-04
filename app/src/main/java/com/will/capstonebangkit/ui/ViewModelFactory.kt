package com.will.capstonebangkit.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.will.capstonebangkit.data.di.Injection
import com.will.capstonebangkit.data.repository.NewsRepository
import com.will.capstonebangkit.ui.home.HomeFragment
import com.will.capstonebangkit.ui.home.HomeViewModel
import com.will.capstonebangkit.ui.news.NewsViewModel

class ViewModelFactory(private val newsRepository: NewsRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                NewsViewModel(newsRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(newsRepository) as T
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
                        Injection.provideNewsRepository(context)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}