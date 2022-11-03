package com.arammeem.android.apps.newsapp.feature.sources.di

import androidx.annotation.RestrictTo
import com.arammeem.android.apps.newsapp.core.database.NewsDatabase
import com.arammeem.android.apps.newsapp.core.di.FeatureScope
import com.arammeem.android.apps.newsapp.core.navigation.ScreenProvider
import com.arammeem.android.apps.newsapp.feature.sources.presentation.SourcesViewModel
import dagger.Component
import retrofit2.Retrofit
import kotlin.properties.Delegates.notNull

@Component(
    modules = [
        SourcesModule::class,
        NetworkModule::class,
        NavigationModule::class,
    ],
    dependencies = [SourcesDependencies::class]
)
@FeatureScope
internal interface SourcesComponent {

    fun sourcesViewModelFactory(): SourcesViewModel.Factory

    @Component.Builder
    interface Builder {
        fun dependencies(sourcesDependencies: SourcesDependencies): Builder

        fun build(): SourcesComponent
    }
}

interface SourcesDependencies {
    val retrofit: Retrofit
    val screenProvider: ScreenProvider
    val database: NewsDatabase
}

interface SourcesDependenciesProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val dependencies: SourcesDependencies

    companion object : SourcesDependenciesProvider by SourcesDependenciesStore
}

object SourcesDependenciesStore : SourcesDependenciesProvider {

    override var dependencies: SourcesDependencies by notNull()
}