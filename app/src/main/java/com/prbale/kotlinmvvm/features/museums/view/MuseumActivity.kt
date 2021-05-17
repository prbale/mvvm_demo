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
import com.prbale.kotlinmvvm.base.extensions.show
import com.prbale.kotlinmvvm.features.museums.model.MuseumList
import com.prbale.kotlinmvvm.features.museums.viewmodel.MuseumViewModel
import kotlinx.android.synthetic.main.activity_museum.*
import kotlinx.android.synthetic.main.layout_error.*

class MuseumActivity : AppCompatActivity() {

    private lateinit var viewModel: MuseumViewModel
    private lateinit var adapter: MuseumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museum)

        setupViewModel()
        setupUI()
    }

    private fun setupUI() {
        adapter = MuseumAdapter(viewModel.getMuseums().value?.data?.data ?: emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        clearBtn?.setOnClickListener {
            adapter.update(emptyList())
        }

        loadBtn?.setOnClickListener {
            viewModel.loadMuseums()
        }
    }

    //view model
    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(MuseumViewModel::class.java)

        // Set Observers
        viewModel.getMuseums()
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

        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
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
    }

    private fun hideLoading() {
        progressBar.gone()
    }

    private fun displayError(errMsg: String?) {
        layoutError.show()
        textViewError.text = errMsg

        progressBar.gone()
        recyclerView?.gone()
        layoutEmpty.gone()
    }

    private fun showEmptyList() {
        progressBar.gone()
        recyclerView?.gone()
        layoutError.gone()
        layoutEmpty.show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMuseums()
    }
}
