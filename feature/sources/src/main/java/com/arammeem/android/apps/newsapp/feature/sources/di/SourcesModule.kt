package com.arammeem.android.apps.newsapp.feature.sources.di

import com.arammeem.android.apps.newsapp.core.di.FeatureScope
import com.arammeem.android.apps.newsapp.feature.sources.data.SourcesPersistentRepositoryImpl
import com.arammeem.android.apps.newsapp.feature.sources.data.SourcesRepositoryImpl
import com.arammeem.android.apps.newsapp.feature.sources.model.SourcesDataSource
import com.arammeem.android.apps.newsapp.feature.sources.model.SourcesDataSourceImpl
import com.arammeem.android.apps.newsapp.feature.sources.model.SourcesPersistentRepository
import com.arammeem.android.apps.newsapp.feature.sources.model.SourcesRepository
import dagger.Binds
import dagger.Module

@Module
internal interface SourcesModule {

    @Binds
    @FeatureScope
    fun sourcesRepository(sourcesRepository: SourcesRepositoryImpl): SourcesRepository

    @Binds
    @FeatureScope
    fun sourcesPersistentRepository(
       sourcesPersistentRepository: SourcesPersistentRepositoryImpl
    ): SourcesPersistentRepository

    @Binds
    @FeatureScope
    fun sourcesDataSource(sourcesDataSource: SourcesDataSourceImpl): SourcesDataSource
}
