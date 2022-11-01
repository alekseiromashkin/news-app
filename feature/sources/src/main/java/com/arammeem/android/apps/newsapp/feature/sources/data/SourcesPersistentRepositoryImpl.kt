package com.arammeem.android.apps.newsapp.feature.sources.data

import com.arammeem.android.apps.newsapp.core.database.NewsDatabase
import com.arammeem.android.apps.newsapp.core.database.model.SourcePersistentEntity
import com.arammeem.android.apps.newsapp.feature.sources.data.entity.Category
import com.arammeem.android.apps.newsapp.feature.sources.data.entity.Source
import com.arammeem.android.apps.newsapp.feature.sources.model.SourcesPersistentRepository
import com.arammeem.android.apps.newsapp.feature.sources.model.SourcesRepository
import javax.inject.Inject

internal class SourcesPersistentRepositoryImpl @Inject constructor(
    private val database: NewsDatabase,
    private val repository: SourcesRepository,
) : SourcesPersistentRepository {
    override suspend fun getSources(
        category: String?,
        language: String?,
        country: String?
    ): List<Source> {
        val result = kotlin.runCatching {
            val sources = repository.getSources(category, language, country)
            database.sourceDao().insert(sources.map { it.toPersistentEntity() })
            sources
        }

        return if (result.isSuccess) {
            result.getOrNull().orEmpty()
        } else {
            database.sourceDao().getSources().map { it.toSource() }
        }
    }
}

private fun Source.toPersistentEntity(): SourcePersistentEntity {
    return SourcePersistentEntity(
        id = id,
        name = name,
        description = description,
        url = url,
        category = category.name.lowercase(),
        language = language,
        country = country,
    )
}

private fun SourcePersistentEntity.toSource(): Source {
    return Source(
        id = id,
        name = name.orEmpty(),
        description = description.orEmpty(),
        url = url.orEmpty(),
        category = category?.let { Category.valueOf(it.uppercase()) } ?: Category.GENERAL,
        language = language.orEmpty(),
        country = country.orEmpty(),
    )
}
