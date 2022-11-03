package com.arammeem.android.apps.newsapp.feature.articles.di

import com.arammeem.android.apps.newsapp.core.di.FeatureScope
import com.arammeem.android.apps.newsapp.feature.articles.data.network.ArticlesApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
internal class ArticlesNetworkModule {

    @Provides
    @FeatureScope
    fun articlesApi(retrofit: Retrofit): ArticlesApi {
        return retrofit.create(ArticlesApi::class.java)
    }
}
