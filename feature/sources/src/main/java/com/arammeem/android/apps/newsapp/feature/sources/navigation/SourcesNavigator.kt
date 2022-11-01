package com.arammeem.android.apps.newsapp.feature.sources.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.arammeem.android.apps.newsapp.core.navigation.SCREEN_ARGUMENT_SOURCE
import com.arammeem.android.apps.newsapp.core.navigation.SCREEN_ARGUMENT_TITLE
import com.arammeem.android.apps.newsapp.core.navigation.SCREEN_ARTICLES
import com.arammeem.android.apps.newsapp.core.navigation.ScreenProvider
import javax.inject.Inject
import javax.inject.Named

internal interface SourcesNavigator {
    fun pushArticlesScreen(fragmentManager: FragmentManager, source: String, title: String)
}

internal class SourcesNavigatorImpl @Inject constructor(
    @Named("nav_container") @IdRes private val containerResId: Int,
    private val screenProvider: ScreenProvider,
) : SourcesNavigator {

    override fun pushArticlesScreen(
        fragmentManager: FragmentManager,
        source: String,
        title: String
    ) {
        screenProvider.getScreen(SCREEN_ARTICLES)?.let { fragment ->
            fragment.arguments = Bundle()
                .apply {
                    putString(SCREEN_ARGUMENT_SOURCE, source)
                    putString(SCREEN_ARGUMENT_TITLE, title)
                }
            fragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(containerResId, fragment, SCREEN_ARTICLES)
                .commit()
        }
    }
}
