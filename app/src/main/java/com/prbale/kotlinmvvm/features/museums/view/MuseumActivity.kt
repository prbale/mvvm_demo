package com.prbale.kotlinmvvm.features.museums.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.prbale.kotlinmvvm.R
import com.prbale.kotlinmvvm.data.remote.api.Resource
import com.prbale.kotlinmvvm.data.remote.api.Resource.Status.*
import com.prbale.kotlinmvvm.extensions.*
import com.prbale.kotlinmvvm.features.museums.model.MuseumList
import com.prbale.kotlinmvvm.features.museums.view.adapter.MuseumAdapter
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

            // Using Retrofit - enqueue call
            //museumViewModel.loadMuseums()

            // Using Retrofit - RxJava
            museumViewModel.fetchMuseums()
        }
    }

    //view model
    private fun setupViewModel() {

        // Create
        museumViewModel = obtainViewModel(MuseumViewModel::class.java)

        // Set Observers
        museumViewModel.getMuseums().observe(this, Observer {
            handleMuseumDataState(it)
        })

    }

    private fun handleMuseumDataState(it: Resource<MuseumList>) {
        when (it.status) {
            LOADING -> {
                showLoading()
            }
            SUCCESS -> {
                hideLoading()
                displayMuseums(it.data)
            }
            ERROR -> {
                hideLoading()
                displayError(it.message)
            }
        }
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
