package com.prbale.kotlinmvvm.base.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.prbale.kotlinmvvm.base.di.Injection

fun <T : ViewModel> ViewModelStoreOwner.getViewModel(modelClass: Class<T>): T {
    return ViewModelProvider(
        this,
        Injection.provideViewModelFactory()
    ).get(modelClass)
}
