package com.prbale.kotlinmvvm.features.museums.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.prbale.kotlinmvvm.R
import com.prbale.kotlinmvvm.base.Status
import com.prbale.kotlinmvvm.base.di.Injection
import com.prbale.kotlinmvvm.base.extensions.gone
import com.prbale.kotlinmvvm.base.extensions.hide
import com.prbale.kotlinmvvm.base.extensions.show
import com.prbale.kotlinmvvm.features.museums.model.MuseumList
import com.prbale.kotlinmvvm.features.museums.viewmodel.MuseumViewModel
import kotlinx.android.synthetic.main.activity_museum.*
import kotlinx.android.synthetic.main.layout_error.*

class MuseumActivity : AppCompatActivity() {

    private lateinit var museumViewModel: MuseumViewModel
    private lateinit var adapter: MuseumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museum)

        setupViewModel()
        setupUI()
    }

    private fun setupUI() {
        adapter = MuseumAdapter(museumViewModel.getMuseums().value?.data?.data ?: emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        clearBtn?.setOnClickListener {
            adapter.update(emptyList())
        }

        loadBtn?.setOnClickListener {
            museumViewModel.loadMuseums()
        }
    }

    //view model
    private fun setupViewModel() {
        museumViewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(MuseumViewModel::class.java)

        // Set Observers
        museumViewModel.getMuseums()
            .observe(this, Observer {
                when(it.status) {
                    Status.LOADING -> {
                        showLoading()
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        displayMuseums(it.data)
                    }
                    Status.ERROR -> {
                        hideLoading()
                        displayError(it.message)
                    }
                }
            })


    }

    // Success
    private fun displayMuseums(data: MuseumList?) {

        hide(layoutError, layoutEmpty)
        recyclerView?.visibility = View.VISIBLE

        data?.data?.let {
            if(it.isEmpty()) {
                showEmptyList()
            }
            else {
                adapter.update(data.data)
            }
        }

    }

    // Loading
    private fun showLoading() {
        progressBar.show()
        hide(recyclerView, layoutEmpty, layoutError)
    }

    private fun hideLoading() {
        progressBar.gone()
    }

    private fun displayError(errMsg: String?) {
        layoutError.show()
        textViewError.text = errMsg
        hide(progressBar, recyclerView, layoutEmpty)
    }

    private fun showEmptyList() {
        hide(progressBar, recyclerView, layoutError)
        layoutEmpty.show()
    }

    override fun onResume() {
        super.onResume()

        // Load on app launch
        museumViewModel.loadMuseums()
    }
}
