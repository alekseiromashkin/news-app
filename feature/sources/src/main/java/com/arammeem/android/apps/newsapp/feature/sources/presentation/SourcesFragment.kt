package com.arammeem.android.apps.newsapp.feature.sources.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arammeem.android.apps.newsapp.core.viewmodel.asContent
import com.arammeem.android.apps.newsapp.core.viewmodel.asError
import com.arammeem.android.apps.newsapp.core.viewmodel.lazyViewModel
import com.arammeem.android.apps.newsapp.feature.sources.R
import com.arammeem.android.apps.newsapp.core.common.R as RC
import com.arammeem.android.apps.newsapp.feature.sources.di.DaggerSourcesComponent
import com.arammeem.android.apps.newsapp.feature.sources.di.SourcesComponent
import com.arammeem.android.apps.newsapp.feature.sources.di.SourcesDependenciesProvider
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal interface OnItemClickListener {
    fun onItemClick(source: String, title: String)
}

class SourcesFragment : Fragment(R.layout.fragment_sources), OnItemClickListener {

    private lateinit var sourcesComponent: SourcesComponent

    private val viewModel: SourcesViewModel by lazyViewModel {
        sourcesComponent.sourcesViewModelFactory().create()
    }

    private val sourcesAdapter = SourcesListAdapter(this).apply {
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }
    private lateinit var layoutSwipe: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        sourcesComponent = DaggerSourcesComponent
            .builder()
            .dependencies(SourcesDependenciesProvider.dependencies)
            .build()
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        collectViewState()
    }

    override fun onItemClick(source: String, title: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.trySend(OpenArticlesScreen(parentFragmentManager, source, title))
        }
    }

    private fun initViews(view: View) {
        val listSources = view.findViewById(R.id.list_sources) as RecyclerView
        listSources.adapter = sourcesAdapter
        listSources.setHasFixedSize(true)

        layoutSwipe = view.findViewById(R.id.layout_swipe) as SwipeRefreshLayout
        layoutSwipe.setOnRefreshListener { collectViewState() }
    }

    private fun collectViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.viewState.collectLatest { render(it) }
            }
        }
    }

    private fun render(state: SourcesViewState) {
        state.sourcesState
            .asContent {
                this?.let { sourcesAdapter.setData(it) }
                layoutSwipe.isRefreshing = false
            }
            .asError {
                layoutSwipe.isRefreshing = false
                showError(this)
            }
    }

    private fun showError(throwable: Throwable?) {
        val message = throwable?.message ?: getString(RC.string.error_unknown)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
