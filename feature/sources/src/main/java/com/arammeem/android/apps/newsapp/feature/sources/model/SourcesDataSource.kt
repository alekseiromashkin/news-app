package com.arammeem.android.apps.newsapp.feature.sources.model

import com.arammeem.android.apps.newsapp.core.viewmodel.LcenState
import com.arammeem.android.apps.newsapp.feature.sources.model.entity.SourceEntity
import com.arammeem.android.apps.newsapp.feature.sources.model.entity.toSourceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal interface SourcesDataSource {
    val sourcesFlow: Flow<LcenState<List<SourceEntity>>>
}

internal class SourcesDataSourceImpl @Inject constructor(
    private val sourcesRepository: SourcesPersistentRepository,
) : SourcesDataSource {

    override val sourcesFlow = flow {
        emit(LcenState.Loading)
        runCatching {
            val result = sourcesRepository
                .getSources()
                .sortedBy { it.language }
                .map { it.toSourceEntity() }
            emit(LcenState.Content(result))
        }.onFailure { throwable ->
            throwable.printStackTrace()
            emit(LcenState.Error(throwable))
        }
    }
}
