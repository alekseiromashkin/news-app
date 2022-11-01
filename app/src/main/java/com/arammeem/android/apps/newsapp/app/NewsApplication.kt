package com.arammeem.android.apps.newsapp.app

import android.app.Application
import com.arammeem.android.apps.newsapp.app.di.ApplicationComponent
import com.arammeem.android.apps.newsapp.app.di.DaggerApplicationComponent
import com.arammeem.android.apps.newsapp.feature.articles.di.ArticlesDependenciesStore
import com.arammeem.android.apps.newsapp.feature.sources.di.SourcesDependenciesStore
import com.arammeem.android.apps.newsapp.launcher.di.LauncherDependenciesStore

class NewsApplication : Application() {

    private val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationContext(this)
            .screenProvider(buildScreenProvider())
            .retrofit(buildRetrofit())
            .database(buildDatabase(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        initDependencies()
    }

    private fun initDependencies() {
        ArticlesDependenciesStore.dependencies = applicationComponent
        LauncherDependenciesStore.dependencies = applicationComponent
        SourcesDependenciesStore.dependencies = applicationComponent
    }
}
