package com.arammeem.android.apps.newsapp.launcher.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.arammeem.android.apps.newsapp.core.navigation.SCREEN_SOURCES
import com.arammeem.android.apps.newsapp.core.navigation.ScreenProvider
import javax.inject.Inject
import javax.inject.Named

internal interface LauncherNavigator {
    fun pushSourcesScreen(fragmentManager: FragmentManager)
}

internal class LauncherNavigatorImpl @Inject constructor(
    @Named("nav_container") @IdRes private val containerResId: Int,
    private val screenProvider: ScreenProvider,
) : LauncherNavigator {

    override fun pushSourcesScreen(fragmentManager: FragmentManager) {
        screenProvider.getScreen(SCREEN_SOURCES)?.let { fragment ->
            fragmentManager
                .beginTransaction()
                .replace(containerResId, fragment, SCREEN_SOURCES)
                .commit()
        }
    }
}
