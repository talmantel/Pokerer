package com.it.pokerer.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.it.pokerer.data.Repository
import com.it.pokerer.ui.main.MainViewModel

class MainViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}