package com.arammeem.android.apps.newsapp.feature.sources.presentation

import com.arammeem.android.apps.newsapp.core.viewmodel.LcenState.Content
import com.arammeem.android.apps.newsapp.core.viewmodel.LcenState.Loading
import com.arammeem.android.apps.newsapp.core.viewmodel.LcenState.Error
import com.arammeem.android.apps.newsapp.feature.sources.data.entity.Category
import com.arammeem.android.apps.newsapp.feature.sources.data.entity.Source
import com.arammeem.android.apps.newsapp.feature.sources.model.SourcesDataSourceImpl
import com.arammeem.android.apps.newsapp.feature.sources.model.SourcesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test
import org.mockito.kotlin.mock
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

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

class SourcesViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var sourcesRepository = fakeSourcesRepository()
    private var throwableSourcesRepository = fakeThrowableSourcesRepository()

    @Test
    fun should_initially_loading_state_then_content() = runTest {
        val viewModel = SourcesViewModel(
            sources = SourcesDataSourceImpl(sourcesRepository),
            navigator = mock(),
        )
        var i = 0
        viewModel.viewState.collect {
            when(i) {
                0 -> assertTrue(it.sourcesState is Loading)
                1 -> assertTrue(it.sourcesState is Content<*>)
            }
            i++
        }
    }

    @Test
    fun should_initially_loading_state_then_error() = runTest {
        val viewModel = SourcesViewModel(
            sources = SourcesDataSourceImpl(throwableSourcesRepository),
            navigator = mock(),
        )
        var i = 0
        viewModel.viewState.collect {
            when(i) {
                0 -> assertTrue(it.sourcesState is Loading)
                1 -> assertTrue(it.sourcesState is Error)
            }
            i++
        }
    }

    private fun fakeSourcesRepository(): SourcesRepository {
        return object : SourcesRepository {
            override suspend fun getSources(
                category: String?,
                language: String?,
                country: String?
            ): List<Source> {
                return listOf(
                    Source(
                        id = "1",
                        name = "Fake source",
                        description = "This is the fake source",
                        url = "google.com",
                        category = Category.BUSINESS,
                        language = "en",
                        country = "fr",
                    )
                )
            }
        }
    }

    private fun fakeThrowableSourcesRepository(): SourcesRepository {
        return object : SourcesRepository {
            override suspend fun getSources(
                category: String?,
                language: String?,
                country: String?
            ): List<Source> {
                throw IllegalStateException()
            }
        }
    }
}
