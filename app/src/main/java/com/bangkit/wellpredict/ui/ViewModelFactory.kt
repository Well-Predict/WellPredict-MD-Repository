package com.bangkit.wellpredict.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.wellpredict.data.di.Injection
import com.bangkit.wellpredict.data.repository.DiagnoseRepository
import com.bangkit.wellpredict.data.repository.NewsRepository
import com.bangkit.wellpredict.data.repository.SymptomsRepository
import com.bangkit.wellpredict.data.repository.UserRepository
import com.bangkit.wellpredict.ui.auth.LoginViewModel
import com.bangkit.wellpredict.ui.auth.RegisterViewModel
import com.bangkit.wellpredict.ui.diagnose.DiagnoseResultViewModel
import com.bangkit.wellpredict.ui.diagnose.DiagnoseSearchViewModel
import com.bangkit.wellpredict.ui.diagnose.DiagnoseViewModel
import com.bangkit.wellpredict.ui.history.HistoryViewModel
import com.bangkit.wellpredict.ui.home.HomeViewModel
import com.bangkit.wellpredict.ui.news.NewsViewModel
import com.bangkit.wellpredict.ui.profile.ProfileViewModel

class ViewModelFactory(
    private val newsRepository: NewsRepository,
    private val symptomsRepository: SymptomsRepository,
    private val userRepository: UserRepository,
    private val diagnoseRepository: DiagnoseRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                NewsViewModel(newsRepository) as T
            }

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(newsRepository, userRepository, diagnoseRepository) as T
            }

            modelClass.isAssignableFrom(DiagnoseViewModel::class.java) -> {
                DiagnoseViewModel(symptomsRepository, diagnoseRepository) as T
            }

            modelClass.isAssignableFrom(DiagnoseSearchViewModel::class.java) -> {
                DiagnoseSearchViewModel(symptomsRepository) as T
            }

            modelClass.isAssignableFrom(DiagnoseResultViewModel::class.java) -> {
                DiagnoseResultViewModel(diagnoseRepository, userRepository) as T
            }

            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(diagnoseRepository) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(userRepository) as T
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
                        Injection.provideSymptomsRepository(context),
                        Injection.provideUserRepository(context),
                        Injection.provideDiagnoseRepository(context)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}