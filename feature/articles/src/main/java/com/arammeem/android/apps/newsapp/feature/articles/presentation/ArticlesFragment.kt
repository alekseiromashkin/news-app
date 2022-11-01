package com.arammeem.android.apps.newsapp.feature.articles.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arammeem.android.apps.newsapp.core.navigation.SCREEN_ARGUMENT_TITLE
import com.arammeem.android.apps.newsapp.core.viewmodel.lazyViewModel
import com.arammeem.android.apps.newsapp.feature.articles.di.ArticlesComponent
import com.arammeem.android.apps.newsapp.feature.articles.di.ArticlesDependenciesProvider
import com.arammeem.android.apps.newsapp.feature.articles.di.DaggerArticlesComponent
import com.arammeem.android.apps.newsapp.feature.articles.model.entity.toArticlesQueryParams
import com.arammeem.android.apps.newsapp.feature.articles.R
import com.arammeem.android.apps.newsapp.core.common.R as RC
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ArticlesFragment : Fragment(R.layout.fragment_articles) {

    private lateinit var articlesComponent: ArticlesComponent

    private val viewModel: ArticlesViewModel by lazyViewModel {
        articlesComponent.articlesViewModelFactory().create()
    }

    private val articlesAdapter = ArticlesListAdapter()
    private lateinit var layoutSwipe: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        articlesComponent = DaggerArticlesComponent
            .builder()
            .articlesQueryParams(arguments.toArticlesQueryParams())
            .dependencies(ArticlesDependenciesProvider.dependencies)
            .build()
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        collectViewState()
    }

    private fun initViews(view: View) {
        val toolbar = view.findViewById(R.id.view_toolbar) as Toolbar
        toolbar.setNavigationOnClickListener { parentFragmentManager.popBackStack() }
        arguments?.getString(SCREEN_ARGUMENT_TITLE)?.let { toolbar.title = it }

        val listArticles = view.findViewById(R.id.list_articles) as RecyclerView
        listArticles.adapter = articlesAdapter
        listArticles.setHasFixedSize(true)

        layoutSwipe = view.findViewById(R.id.layout_swipe) as SwipeRefreshLayout
        layoutSwipe.setOnRefreshListener { articlesAdapter.refresh() }

        articlesAdapter.addLoadStateListener { state ->
            state.toErrorState()?.let { showError(it.error) }
        }
    }

    private fun collectViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.articlesDataFlow
                    .collectLatest {
                        layoutSwipe.isRefreshing = false
                        articlesAdapter.submitData(it)
                    }
            }
        }
    }

    private fun showError(throwable: Throwable) {
        val message = throwable.message ?: getString(RC.string.error_unknown)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

private fun CombinedLoadStates.toErrorState(): LoadState.Error? {
    return when {
        append is LoadState.Error -> append as LoadState.Error
        prepend is LoadState.Error ->  prepend as LoadState.Error
        refresh is LoadState.Error -> refresh as LoadState.Error
        else -> null
    }
}