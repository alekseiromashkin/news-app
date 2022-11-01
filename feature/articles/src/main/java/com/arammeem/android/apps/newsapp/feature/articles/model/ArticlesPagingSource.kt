package com.arammeem.android.apps.newsapp.feature.articles.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arammeem.android.apps.newsapp.core.errors.ServerException
import com.arammeem.android.apps.newsapp.feature.articles.model.entity.ArticleEntity
import com.arammeem.android.apps.newsapp.feature.articles.model.entity.ArticlesQueryParams
import com.arammeem.android.apps.newsapp.feature.articles.model.entity.toArticleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val PAGING_START_INDEX = 1
internal const val NETWORK_PAGE_SIZE = 20

internal class ArticlesPagingSource @Inject constructor(
    private val repository: ArticlesPersistentRepository,
    private val queryParams: ArticlesQueryParams,
) : PagingSource<Int, ArticleEntity>() {

    override fun getRefreshKey(state: PagingState<Int, ArticleEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleEntity> {
        val pageIndex = params.key ?: PAGING_START_INDEX
        val sourceList = queryParams.source?.let { listOf(it) }
        return try {
            withContext(Dispatchers.IO) {
                val articles = repository
                    .getArticles(
                        sources = sourceList,
                        pageSize = NETWORK_PAGE_SIZE,
                        page = pageIndex
                    )
                    .map { it.toArticleEntity() }
                LoadResult.Page(
                    data = articles,
                    prevKey = if (pageIndex == PAGING_START_INDEX) null else pageIndex - 1,
                    nextKey = if (articles.isEmpty()) null else pageIndex + 1
                )
            }
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: ServerException) {
            LoadResult.Error(exception)
        }
    }
}
