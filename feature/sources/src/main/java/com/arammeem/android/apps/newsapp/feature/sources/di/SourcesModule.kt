package com.arammeem.android.apps.newsapp.feature.sources.di

import androidx.annotation.IdRes
import com.arammeem.android.apps.newsapp.core.di.FeatureScope
import com.arammeem.android.apps.newsapp.core.navigation.ScreenProvider
import com.arammeem.android.apps.newsapp.core.common.R
import com.arammeem.android.apps.newsapp.core.database.NewsDatabase
import com.arammeem.android.apps.newsapp.feature.sources.data.SourcesPersistentRepositoryImpl
import com.arammeem.android.apps.newsapp.feature.sources.data.SourcesRepositoryImpl
import com.arammeem.android.apps.newsapp.feature.sources.model.SourcesDataSource
import com.arammeem.android.apps.newsapp.feature.sources.model.SourcesDataSourceImpl
import com.arammeem.android.apps.newsapp.feature.sources.model.SourcesPersistentRepository
import com.arammeem.android.apps.newsapp.feature.sources.model.SourcesRepository
import com.arammeem.android.apps.newsapp.feature.sources.navigation.SourcesNavigator
import com.arammeem.android.apps.newsapp.feature.sources.navigation.SourcesNavigatorImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
internal class SourcesModule {

    @Provides
    @FeatureScope
    fun sourcesRepository(retrofit: Retrofit): SourcesRepository {
        return SourcesRepositoryImpl(retrofit)
    }

    @Provides
    @FeatureScope
    fun sourcesPersistentRepository(
        database: NewsDatabase,
        sourcesRepository: SourcesRepository
    ): SourcesPersistentRepository {
        return SourcesPersistentRepositoryImpl(database, sourcesRepository)
    }

    @Provides
    @FeatureScope
    fun sourcesListUseCase(
        sourcesPersistentRepository: SourcesPersistentRepository
    ): SourcesDataSource {
        return SourcesDataSourceImpl(sourcesPersistentRepository)
    }

    @Provides
    @FeatureScope
    fun sourcesNavigator(
        @Named("nav_container") @IdRes containerResId: Int,
        screenProvider: ScreenProvider,
    ): SourcesNavigator {
        return SourcesNavigatorImpl(containerResId, screenProvider)
    }

    @Provides
    @FeatureScope
    @Named("nav_container")
    fun provideContainerResId(): Int = R.id.content
}
