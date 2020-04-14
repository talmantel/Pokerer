package com.it.pokerer.utils

import android.app.Application
import com.it.pokerer.data.RepositoryImpl
import com.it.pokerer.data.ScoresSharedPrefs
import com.it.pokerer.ui.main.MainViewModelFactory

object InjectorUtils {

    fun provideMainViewModelFactory(
        application: Application
    ): MainViewModelFactory {
        val repository = RepositoryImpl(ScoresSharedPrefs(application))
        return MainViewModelFactory(repository)
    }
}