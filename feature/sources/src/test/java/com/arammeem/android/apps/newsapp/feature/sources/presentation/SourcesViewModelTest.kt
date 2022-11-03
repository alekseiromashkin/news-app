package com.arammeem.android.apps.newsapp.feature.sources.presentation

import com.arammeem.android.apps.newsapp.core.database.NewsDatabase
import com.arammeem.android.apps.newsapp.core.database.SourceDao
import com.arammeem.android.apps.newsapp.core.viewmodel.LcenState.Content
import com.arammeem.android.apps.newsapp.core.viewmodel.LcenState.Loading
import com.arammeem.android.apps.newsapp.feature.sources.data.SourcesPersistentRepositoryImpl
import com.arammeem.android.apps.newsapp.feature.sources.data.SourcesRepositoryImpl
import com.arammeem.android.apps.newsapp.feature.sources.data.network.SourcesApi
import com.arammeem.android.apps.newsapp.feature.sources.data.network.SourcesResponse
import com.arammeem.android.apps.newsapp.feature.sources.model.SourcesDataSourceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test
import org.mockito.kotlin.mock
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.given
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

internal class SourcesViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var sourceDao: SourceDao

    @Mock
    lateinit var sourcesApi: SourcesApi

    @Mock
    lateinit var newsDatabase: NewsDatabase

    lateinit var viewModel: SourcesViewModel

    private val sourcesResponse = Response.success(
        SourcesResponse(
            status = "ok",
            sources = emptyList()
        )
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        val repository = SourcesPersistentRepositoryImpl(
            newsDatabase,
            SourcesRepositoryImpl(sourcesApi),
        )
        given(sourceDao.getSources()).willReturn(emptyList())
        given(newsDatabase.sourceDao()).willReturn(sourceDao)

        viewModel = SourcesViewModel(
            sources = SourcesDataSourceImpl(repository),
            navigator = mock(),
        )
    }

    @Test
    fun should_initially_loading_state_then_content() = runTest {
        given(sourcesApi.getSources(any(), any(), any())).willReturn(sourcesResponse)
        var i = 0
        viewModel.viewState.collect {
            when(i) {
                0 -> assertTrue(it.sourcesState is Loading)
                1 -> assertTrue(it.sourcesState is Content<*>)
            }
            i++
        }
    }
}
