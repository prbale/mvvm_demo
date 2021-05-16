package com.prbale.kotlinmvvm.base.di

import com.prbale.kotlinmvvm.base.ViewModelFactory
import com.prbale.kotlinmvvm.base.api.ApiClient
import com.prbale.kotlinmvvm.features.museums.model.MuseumDataSource
import com.prbale.kotlinmvvm.features.museums.model.MuseumRepository
import com.prbale.kotlinmvvm.features.museums.model.data.MuseumRemoteDataSource

object Injection {

    private val museumDataSource: MuseumDataSource = MuseumRemoteDataSource(ApiClient)
    private val museumRepository = MuseumRepository(museumDataSource)
    private val museumViewModelFactory = ViewModelFactory(museumRepository)

    fun provideViewModelFactory() = museumViewModelFactory
}