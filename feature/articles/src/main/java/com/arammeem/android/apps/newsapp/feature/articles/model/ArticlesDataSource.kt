package com.arammeem.android.apps.newsapp.feature.articles.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arammeem.android.apps.newsapp.feature.articles.model.entity.ArticleEntity
import com.arammeem.android.apps.newsapp.feature.articles.model.entity.ArticlesQueryParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal interface ArticlesDataSource {
    fun getArticles(): Flow<PagingData<ArticleEntity>>
}

internal class ArticlesDataSourceImpl @Inject constructor(
    private val repository: ArticlesPersistentRepository,
    private val queryParams: ArticlesQueryParams,
) : ArticlesDataSource {

    override fun getArticles(): Flow<PagingData<ArticleEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ArticlesPagingSource(
                    repository = repository,
                    queryParams = queryParams,
                )
            }
        ).flow
    }
}
