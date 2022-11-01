package com.arammeem.android.apps.newsapp.launcher.di

import androidx.annotation.RestrictTo
import com.arammeem.android.apps.newsapp.core.di.ActivityScope
import com.arammeem.android.apps.newsapp.core.navigation.ScreenProvider
import com.arammeem.android.apps.newsapp.launcher.presentation.LauncherActivity
import com.arammeem.android.apps.newsapp.launcher.presentation.LauncherViewModel
import dagger.Component
import kotlin.properties.Delegates

@Component(
    modules = [LauncherModule::class],
    dependencies = [LauncherDependencies::class]
)
@ActivityScope
internal interface LauncherComponent {

    fun inject(activity: LauncherActivity)

    fun launcherViewModel(): LauncherViewModel.Factory

    @Component.Builder
    interface Builder {
        fun dependencies(sourcesDependencies: LauncherDependencies): Builder
        fun build(): LauncherComponent
    }
}

internal interface LauncherDependencies {
    val screenProvider: ScreenProvider
}

internal interface LauncherDependenciesProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val dependencies: LauncherDependencies

    companion object : LauncherDependenciesProvider by LauncherDependenciesStore
}

internal object LauncherDependenciesStore : LauncherDependenciesProvider {

    override var dependencies: LauncherDependencies by Delegates.notNull()
}