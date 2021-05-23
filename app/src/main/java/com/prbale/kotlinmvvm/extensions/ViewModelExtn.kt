package com.prbale.kotlinmvvm.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prbale.kotlinmvvm.di.Injection

fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>): T {
    return ViewModelProvider(
        this,
        Injection.provideViewModelFactory()
    ).get(viewModelClass)
}


fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>) : T {
    return ViewModelProvider(
        this,
        Injection.provideViewModelFactory()
    ).get(viewModelClass)
}