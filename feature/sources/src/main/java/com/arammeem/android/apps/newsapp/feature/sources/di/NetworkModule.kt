package com.arammeem.android.apps.newsapp.feature.sources.di

import com.arammeem.android.apps.newsapp.core.di.FeatureScope
import com.arammeem.android.apps.newsapp.feature.sources.data.network.SourcesApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
internal class NetworkModule {

    @Provides
    @FeatureScope
    fun sourcesApi(retrofit: Retrofit): SourcesApi {
        return retrofit.create(SourcesApi::class.java)
    }
}
