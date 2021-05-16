package com.prbale.kotlinmvvm.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prbale.kotlinmvvm.features.museums.model.MuseumRepository
import com.prbale.kotlinmvvm.features.museums.viewmodel.MuseumViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: MuseumRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(MuseumViewModel::class.java)) {
            return MuseumViewModel(repository) as T
        }

        throw  IllegalArgumentException("Unknown class name")
    }
}