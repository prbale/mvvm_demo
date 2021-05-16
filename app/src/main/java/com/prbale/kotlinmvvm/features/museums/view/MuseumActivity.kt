package com.prbale.kotlinmvvm.features.museums.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.prbale.kotlinmvvm.R
import com.prbale.kotlinmvvm.base.di.Injection
import com.prbale.kotlinmvvm.features.museums.model.Museum

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
        adapter = MuseumAdapter(viewModel.museums.value ?: emptyList())
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
        viewModel.museums.observe(this, renderMuseums)
        viewModel.isViewLoading.observe(this, isViewLoadingObserver)
        viewModel.onMessageError.observe(this, onMessageErrorObserver)
        viewModel.isEmptyList.observe(this, emptyListObserver)
    }

    // Success
    private val renderMuseums = Observer<List<Museum>> {

        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
        progressBar.visibility = View.GONE
        recyclerView?.visibility = View.VISIBLE

        adapter.update(it)
    }

    // Loading
    private val isViewLoadingObserver = Observer<Boolean> {
        progressBar.visibility = if (it) View.VISIBLE else View.GONE

        recyclerView?.visibility = View.GONE
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
    }

    private val onMessageErrorObserver = Observer<Any> {
        layoutError.visibility = View.VISIBLE
        textViewError.text = "$it"

        progressBar.visibility = View.GONE
        recyclerView?.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
    }

    private val emptyListObserver = Observer<Boolean> {
        progressBar.visibility = View.GONE
        recyclerView?.visibility = View.GONE
        layoutError.visibility = View.GONE
        layoutEmpty.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMuseums()
    }
}
