package com.arammeem.android.apps.newsapp.feature.sources.model.entity

import com.arammeem.android.apps.newsapp.feature.sources.data.entity.Source

internal data class SourceEntity(
    val id: String,
    val name: String,
    val description: String,
    val url: String,
)

internal fun Source.toSourceEntity(): SourceEntity {
    return SourceEntity(
        id = id,
        name = name,
        description = description,
        url = url,
    )
}
