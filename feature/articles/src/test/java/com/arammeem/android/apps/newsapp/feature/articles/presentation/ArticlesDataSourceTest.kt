package com.arammeem.android.apps.newsapp.feature.articles.presentation

import androidx.paging.PagingSource
import com.arammeem.android.apps.newsapp.core.database.ArticleDao
import com.arammeem.android.apps.newsapp.core.database.NewsDatabase
import com.arammeem.android.apps.newsapp.feature.articles.data.ArticlesPersistentRepositoryImpl
import com.arammeem.android.apps.newsapp.feature.articles.data.ArticlesRepositoryImpl
import com.arammeem.android.apps.newsapp.feature.articles.data.entity.Article
import com.arammeem.android.apps.newsapp.feature.articles.data.network.*
import com.arammeem.android.apps.newsapp.feature.articles.model.*
import com.arammeem.android.apps.newsapp.feature.articles.model.entity.ArticlesQueryParams
import com.arammeem.android.apps.newsapp.feature.articles.model.entity.toArticleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*
import retrofit2.Response

class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

internal class ArticlesDataSourceTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var articlesDao: ArticleDao

    @Mock
    lateinit var articlesApi: ArticlesApi

    @Mock
    lateinit var newsDatabase: NewsDatabase

    private lateinit var articlesPagingSource: ArticlesPagingSource

    private val articlesResponse = Response.success(
        ArticlesResponse(
            status = "ok",
            totalResults = 10,
            articles = listOf(
                ArticleNetworkEntity(SourceNetworkEntity("", ""))
            )
        )
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        val repository = ArticlesPersistentRepositoryImpl(
            newsDatabase,
            ArticlesRepositoryImpl(articlesApi),
        )
        articlesPagingSource = ArticlesPagingSource(
            repository = repository,
            queryParams = ArticlesQueryParams()
        )
        given(articlesDao.getArticles(any(), any(), any())).willReturn(emptyList())
        given(newsDatabase.articleDao()).willReturn(articlesDao)
    }

    @Test
    fun `articles paging source load - failure - empty result`() = runTest {
        val error = RuntimeException("404", Throwable())
        given(articlesApi.getArticles(any(), any(), any(), any(), any(), any()))
            .willThrow(error)
        val expectedResult = PagingSource.LoadResult.Page<Int, Article>(emptyList(), -1, null)
        assertEquals(
            expectedResult,
            articlesPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `articles paging source refresh - success`() = runTest {
        given(articlesApi.getArticles(isNull(), isNull(), isNull(), isNull(), eq(20), eq(0)))
            .willReturn(articlesResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = articlesResponse.body()?.toArticlesList()?.map { it.toArticleEntity() }
                .orEmpty(),
            prevKey = -1,
            nextKey = 1
        )
        assertEquals(
            expectedResult,
            articlesPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 20,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `articles paging source append - success`() = runTest {
        given(articlesApi.getArticles(isNull(), isNull(), isNull(), isNull(), eq(20), eq(1)))
            .willReturn(articlesResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = articlesResponse.body()?.toArticlesList()?.map { it.toArticleEntity() }
                .orEmpty(),
            prevKey = null,
            nextKey = 2,
        )
        assertEquals(
            expectedResult,
            articlesPagingSource.load(
                PagingSource.LoadParams.Append(
                    key = 1,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `articles paging source prepend - success`() = runTest {
        given(articlesApi.getArticles(isNull(), isNull(), isNull(), isNull(), eq(20), eq(0)))
            .willReturn(articlesResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = articlesResponse.body()?.toArticlesList()?.map { it.toArticleEntity() }
                .orEmpty(),
            prevKey = -1,
            nextKey = 1
        )
        assertEquals(
            expectedResult,
            articlesPagingSource.load(
                PagingSource.LoadParams.Prepend(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }
}
