package com.arammeem.android.apps.newsapp.feature.articles.data

import com.arammeem.android.apps.newsapp.core.errors.ServerException
import com.arammeem.android.apps.newsapp.core.network.ErrorBody
import com.arammeem.android.apps.newsapp.feature.articles.data.entity.Article
import com.arammeem.android.apps.newsapp.feature.articles.data.network.ArticlesApi
import com.arammeem.android.apps.newsapp.feature.articles.data.network.toArticlesList
import com.arammeem.android.apps.newsapp.feature.articles.model.ArticlesRepository
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import okhttp3.ResponseBody
import javax.inject.Inject

internal class ArticlesRepositoryImpl @Inject constructor(
    private val articlesApi: ArticlesApi
): ArticlesRepository {

    override suspend fun getArticles(
        country: String?,
        category: String?,
        sources: List<String>?,
        q: String?,
        pageSize: Int?,
        page: Int?
    ): List<Article> {
        val sourceList = sources?.joinToString(separator = ",")

        val result = articlesApi.getArticles(country, category, sourceList, q, pageSize, page)
        return if (result.isSuccessful) {
            result.body()?.toArticlesList().orEmpty()
        } else {
            val error = result.errorBody()?.toErrorBody()
            throw ServerException(error?.code, error?.message)
        }
    }
}

@OptIn(ExperimentalSerializationApi::class)
private fun ResponseBody?.toErrorBody(): ErrorBody {
    return if (this != null) {
        Json.decodeFromStream(ErrorBody.serializer(), byteStream())
    } else {
        ErrorBody()
    }
}
