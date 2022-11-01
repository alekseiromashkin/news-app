package com.arammeem.android.apps.newsapp.launcher.presentation

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.arammeem.android.apps.newsapp.launcher.navigation.LauncherNavigator
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

internal class LauncherViewModel @AssistedInject constructor(
    private val navigator: LauncherNavigator,
) : ViewModel() {

    fun openSourcesScreen(fragmentManager: FragmentManager) {
        navigator.pushSourcesScreen(fragmentManager)
    }

    @AssistedFactory
    interface Factory {
        fun create(): LauncherViewModel
    }
}
