package com.arammeem.android.apps.newsapp.feature.articles.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.arammeem.android.apps.newsapp.feature.articles.model.ArticlesDataSource
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

internal class ArticlesViewModel  @AssistedInject constructor(
    articlesDataSource: ArticlesDataSource,
) : ViewModel() {

    val articlesDataFlow = articlesDataSource
        .getArticles()
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)

    @AssistedFactory
    interface Factory {
        fun create(): ArticlesViewModel
    }
}
