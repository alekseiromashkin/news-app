package com.arammeem.android.apps.newsapp.feature.articles.data.network

import com.arammeem.android.apps.newsapp.feature.articles.data.entity.Article
import com.arammeem.android.apps.newsapp.feature.articles.data.entity.Source
import kotlinx.serialization.Serializable

@Serializable
internal data class ArticlesResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleNetworkEntity>? = null
)

@Serializable
internal data class ArticleNetworkEntity(
    val source: SourceNetworkEntity,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null,
)

@Serializable
internal data class SourceNetworkEntity(
    val id: String,
    val name: String,
)

internal fun SourceNetworkEntity.toSource(): Source {
    return Source(
        id = id,
        name = name,
    )
}

internal fun ArticleNetworkEntity.toArticle(): Article {
    return Article(
        source = source.toSource(),
        author = author.orEmpty(),
        title = title.orEmpty(),
        description = description.orEmpty(),
        url = url.orEmpty(),
        urlToImage = urlToImage.orEmpty(),
        publishedAt = publishedAt.orEmpty(),
        content = content.orEmpty(),
    )
}

internal fun ArticlesResponse.toArticlesList(): List<Article> {
    return this.articles?.map { it.toArticle() }.orEmpty()
}
