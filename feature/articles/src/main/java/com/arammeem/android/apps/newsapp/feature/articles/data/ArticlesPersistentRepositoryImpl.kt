package com.arammeem.android.apps.newsapp.feature.articles.data

import com.arammeem.android.apps.newsapp.core.capped
import com.arammeem.android.apps.newsapp.core.database.NewsDatabase
import com.arammeem.android.apps.newsapp.core.database.model.ArticlePersistentEntity
import com.arammeem.android.apps.newsapp.core.toOffsetDateTime
import com.arammeem.android.apps.newsapp.feature.articles.data.entity.Article
import com.arammeem.android.apps.newsapp.feature.articles.data.entity.Source
import com.arammeem.android.apps.newsapp.feature.articles.model.ArticlesPersistentRepository
import com.arammeem.android.apps.newsapp.feature.articles.model.ArticlesRepository
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import javax.inject.Inject

private const val DEFAULT_LIMIT = 20

internal class ArticlesPersistentRepositoryImpl @Inject constructor(
    private val database: NewsDatabase,
    private val repository: ArticlesRepository,
) : ArticlesPersistentRepository {

    override suspend fun getArticles(
        country: String?,
        category: String?,
        sources: List<String>?,
        q: String?,
        pageSize: Int?,
        page: Int?
    ): List<Article> {
        val limit = pageSize ?: DEFAULT_LIMIT
        val offset = limit * ((page ?: 0) - 1).coerceAtLeast(0)
        val result = kotlin.runCatching {
            val articles = repository.getArticles(country, category, sources, q, pageSize, page)
            database.articleDao().insert(articles.map { it.toPersistentEntity() })
            articles
        }.onFailure { it.printStackTrace() }

        return if (result.isSuccess || (page ?: 1) > 1) {
            result.getOrNull().orEmpty()
        } else {
            database.articleDao().getArticles(sources.orEmpty(), limit, offset)
                .map { it.toSource() }
        }
    }
}

private fun Article.toPersistentEntity(): ArticlePersistentEntity {
    return ArticlePersistentEntity(
        sourceId = source.id,
        title = title,
        publishedAt = publishedAt.toOffsetDateTime().toInstant().capped().toEpochMilli(),
        sourceName = source.name,
        author = author,
        description = description,
        url = url,
        urlToImage = urlToImage,
        content = content,
    )
}

private fun ArticlePersistentEntity.toSource(): Article {
    return Article(
        source = Source(sourceId, sourceName),
        author = author.orEmpty(),
        title = title,
        description = description.orEmpty(),
        url = url.orEmpty(),
        urlToImage = urlToImage.orEmpty(),
        publishedAt = publishedAt.toTimestamp(),
        content = content.orEmpty(),
    )
}

private fun Long.toTimestamp(): String {
    return OffsetDateTime
        .ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())
        .toString()
}
