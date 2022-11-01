package com.arammeem.android.apps.newsapp.feature.articles.model

import com.arammeem.android.apps.newsapp.feature.articles.data.entity.Article

internal interface ArticlesPersistentRepository {
    suspend fun getArticles(
        country: String? = null,
        category: String? = null,
        sources: List<String>? = null,
        q: String? = null,
        pageSize: Int? = null,
        page: Int? = null,
    ): List<Article>
}
