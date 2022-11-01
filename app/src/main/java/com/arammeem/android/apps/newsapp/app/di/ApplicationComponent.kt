package com.arammeem.android.apps.newsapp.app.di

import android.content.Context
import android.content.res.Resources
import com.arammeem.android.apps.newsapp.core.database.NewsDatabase
import com.arammeem.android.apps.newsapp.core.di.ApplicationContext
import com.arammeem.android.apps.newsapp.core.navigation.ScreenProvider
import com.arammeem.android.apps.newsapp.feature.articles.di.ArticlesDependencies
import com.arammeem.android.apps.newsapp.feature.sources.di.SourcesDependencies
import com.arammeem.android.apps.newsapp.launcher.di.LauncherDependencies
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class]
)
internal interface ApplicationComponent : SourcesDependencies, LauncherDependencies,
    ArticlesDependencies {

    override val screenProvider: ScreenProvider

    override val retrofit: Retrofit

    override val database: NewsDatabase

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationContext(@ApplicationContext applicationContext: Context): Builder

        @BindsInstance
        fun screenProvider(screenProvider: ScreenProvider): Builder

        @BindsInstance
        fun retrofit(retrofit: Retrofit): Builder

        @BindsInstance
        fun database(database: NewsDatabase) : Builder

        fun build(): ApplicationComponent
    }
}
