package com.arammeem.android.apps.newsapp.feature.sources.data.network

import com.arammeem.android.apps.newsapp.feature.sources.data.entity.Category
import com.arammeem.android.apps.newsapp.feature.sources.data.entity.Source
import kotlinx.serialization.Serializable

@Serializable
internal data class SourcesResponse(
    val status: String,
    val sources: List<SourceNetworkEntity>? = null,
)

@Serializable
internal data class SourceNetworkEntity(
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String,
)

internal fun SourceNetworkEntity.toSource(): Source {
    return Source(
        id = id,
        name = name,
        description = description,
        url = url,
        category = Category.valueOf(category.uppercase()),
        language = language,
        country = language,
    )
}

internal fun SourcesResponse.toSourceList(): List<Source> {
    return this.sources?.map { it.toSource() }.orEmpty()
}
