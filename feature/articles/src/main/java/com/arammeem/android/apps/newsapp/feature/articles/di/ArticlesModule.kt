package com.arammeem.android.apps.newsapp.feature.articles.di

import com.arammeem.android.apps.newsapp.core.di.FeatureScope
import com.arammeem.android.apps.newsapp.feature.articles.data.ArticlesPersistentRepositoryImpl
import com.arammeem.android.apps.newsapp.feature.articles.data.ArticlesRepositoryImpl
import com.arammeem.android.apps.newsapp.feature.articles.model.ArticlesDataSource
import com.arammeem.android.apps.newsapp.feature.articles.model.ArticlesDataSourceImpl
import com.arammeem.android.apps.newsapp.feature.articles.model.ArticlesPersistentRepository
import com.arammeem.android.apps.newsapp.feature.articles.model.ArticlesRepository
import dagger.Binds
import dagger.Module

@Module
internal interface ArticlesModule {

    @Binds
    @FeatureScope
    fun sourcesRepository(articlesRepository: ArticlesRepositoryImpl): ArticlesRepository

    @Binds
    @FeatureScope
    fun sourcesPersistentRepository(
        articlesPersistentRepository: ArticlesPersistentRepositoryImpl
    ): ArticlesPersistentRepository

    @Binds
    @FeatureScope
    fun articlesDataSource(
        articlesDataSourceImpl: ArticlesDataSourceImpl
    ): ArticlesDataSource
}
