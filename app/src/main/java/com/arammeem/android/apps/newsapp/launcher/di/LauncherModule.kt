package com.arammeem.android.apps.newsapp.launcher.di

import com.arammeem.android.apps.newsapp.core.common.R
import com.arammeem.android.apps.newsapp.core.di.ActivityScope
import com.arammeem.android.apps.newsapp.core.navigation.ScreenProvider
import com.arammeem.android.apps.newsapp.launcher.navigation.LauncherNavigator
import com.arammeem.android.apps.newsapp.launcher.navigation.LauncherNavigatorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
internal class LauncherModule {

    @Provides
    @Named("nav_container")
    @ActivityScope
    fun provideContainerResId(): Int = R.id.content

    @Provides
    @ActivityScope
    fun provideLauncherNavigator(
        @Named("nav_container") containerResId: Int,
        screenProvider: ScreenProvider,
    ): LauncherNavigator {
        return LauncherNavigatorImpl(
            containerResId = containerResId,
            screenProvider = screenProvider,
        )
    }
}
