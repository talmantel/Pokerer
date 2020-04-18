package com.it.pokerer.utils

import android.app.Application
import com.it.pokerer.data.RepositoryImpl
import com.it.pokerer.data.RoundRoomDatabase
import com.it.pokerer.data.ScoresSharedPrefs

object InjectorUtils {

    fun provideMainViewModelFactory(
        application: Application
    ): MainViewModelFactory {
        val repository = RepositoryImpl.getInstance(ScoresSharedPrefs.getInstance(application),
            RoundRoomDatabase.getDatabase(application)!!.roundDao())
        return MainViewModelFactory(repository)
    }
}