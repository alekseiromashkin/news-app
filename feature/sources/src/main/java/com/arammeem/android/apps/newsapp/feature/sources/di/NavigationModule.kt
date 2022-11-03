package com.arammeem.android.apps.newsapp.feature.sources.di

import androidx.annotation.IdRes
import com.arammeem.android.apps.newsapp.core.common.R
import com.arammeem.android.apps.newsapp.core.di.FeatureScope
import com.arammeem.android.apps.newsapp.core.navigation.ScreenProvider
import com.arammeem.android.apps.newsapp.feature.sources.navigation.SourcesNavigator
import com.arammeem.android.apps.newsapp.feature.sources.navigation.SourcesNavigatorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
internal class NavigationModule {

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
