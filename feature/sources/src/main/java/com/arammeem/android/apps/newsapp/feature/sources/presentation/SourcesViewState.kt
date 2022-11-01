package com.arammeem.android.apps.newsapp.feature.sources.presentation

import com.arammeem.android.apps.newsapp.core.viewmodel.LcenState
import com.arammeem.android.apps.newsapp.core.viewmodel.toLcen
import com.arammeem.android.apps.newsapp.feature.sources.model.entity.SourceEntity

internal data class SourcesViewState(
    val sourcesState: LcenState<List<SourceEntity>> = LcenState.None
)

internal fun Result<List<SourceEntity>>.toViewState(): SourcesViewState {
    return SourcesViewState(this.toLcen())
}
