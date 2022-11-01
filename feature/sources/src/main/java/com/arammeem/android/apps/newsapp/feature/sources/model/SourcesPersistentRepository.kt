package com.arammeem.android.apps.newsapp.feature.sources.model

import com.arammeem.android.apps.newsapp.feature.sources.data.entity.Source

internal interface SourcesPersistentRepository {
    suspend fun getSources(
        category: String? = null,
        language: String? = null,
        country: String? = null,
    ): List<Source>
}