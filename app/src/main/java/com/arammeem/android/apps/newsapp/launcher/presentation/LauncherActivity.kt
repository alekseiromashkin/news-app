package com.arammeem.android.apps.newsapp.launcher.presentation

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.arammeem.android.apps.newsapp.core.common.R
import com.arammeem.android.apps.newsapp.core.viewmodel.lazyViewModel
import com.arammeem.android.apps.newsapp.launcher.di.DaggerLauncherComponent
import com.arammeem.android.apps.newsapp.launcher.di.LauncherComponent
import com.arammeem.android.apps.newsapp.launcher.di.LauncherDependenciesProvider

class LauncherActivity : FragmentActivity() {

    private lateinit var launcherComponent: LauncherComponent

    private val viewModel: LauncherViewModel by lazyViewModel {
        launcherComponent.launcherViewModel().create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        launcherComponent = DaggerLauncherComponent
            .builder()
            .dependencies(LauncherDependenciesProvider.dependencies)
            .build()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        if (savedInstanceState == null) {
            viewModel.openSourcesScreen(supportFragmentManager)
        }
    }
}
