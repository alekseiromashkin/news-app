package com.arammeem.android.apps.newsapp.feature.sources.presentation

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arammeem.android.apps.newsapp.feature.sources.model.SourcesDataSource
import com.arammeem.android.apps.newsapp.feature.sources.navigation.SourcesNavigator
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class SourcesViewModel @AssistedInject constructor(
    sources: SourcesDataSource,
    private val navigator: SourcesNavigator,
) : ViewModel() {

    val viewState = sources.sourcesFlow
        .flowOn(Dispatchers.IO)
        .distinctUntilChanged()
        .map { SourcesViewState(it) }

    private val _eventChannel = Channel<SourcesEvents>(BUFFERED)
    val events = _eventChannel

    init {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _eventChannel.receiveAsFlow().collect { event ->
                    when (event) {
                        is OpenArticlesScreen -> openArticlesScreen(
                            event.fragmentManager,
                            event.source,
                            event.title
                        )
                    }
                }
            }
        }
    }

    private fun openArticlesScreen(
        fragmentManager: FragmentManager,
        source: String,
        title: String
    ) {
        navigator.pushArticlesScreen(fragmentManager, source, title)
    }

    @AssistedFactory
    interface Factory {
        fun create(): SourcesViewModel
    }
}
