package com.arammeem.android.apps.newsapp.feature.articles.model.entity

import com.arammeem.android.apps.newsapp.core.toOffsetDateTime
import com.arammeem.android.apps.newsapp.feature.articles.data.entity.Article
import java.time.OffsetDateTime

internal data class ArticleEntity(
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val author: String,
    val publishedAt: OffsetDateTime,
)

internal fun Article.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        author = author,
        publishedAt = publishedAt.toOffsetDateTime(),
    )
}
