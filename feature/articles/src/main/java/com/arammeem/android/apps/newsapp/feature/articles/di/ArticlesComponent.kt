package com.arammeem.android.apps.newsapp.feature.articles.di

import androidx.annotation.RestrictTo
import com.arammeem.android.apps.newsapp.core.database.NewsDatabase
import com.arammeem.android.apps.newsapp.core.di.FeatureScope
import com.arammeem.android.apps.newsapp.feature.articles.model.entity.ArticlesQueryParams
import com.arammeem.android.apps.newsapp.feature.articles.presentation.ArticlesViewModel
import dagger.BindsInstance
import dagger.Component
import retrofit2.Retrofit
import kotlin.properties.Delegates

@Component(
    modules = [ArticlesModule::class, ArticlesNetworkModule::class],
    dependencies = [ArticlesDependencies::class]
)
@FeatureScope
internal interface ArticlesComponent {

    fun articlesViewModelFactory(): ArticlesViewModel.Factory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun articlesQueryParams(articlesQueryParams: ArticlesQueryParams): Builder
        fun dependencies(articlesDependencies: ArticlesDependencies): Builder
        fun build(): ArticlesComponent
    }
}

interface ArticlesDependencies {
    val retrofit: Retrofit
    val database: NewsDatabase
}

interface ArticlesDependenciesProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val dependencies: ArticlesDependencies

    companion object : ArticlesDependenciesProvider by ArticlesDependenciesStore
}

object ArticlesDependenciesStore : ArticlesDependenciesProvider {

    override var dependencies: ArticlesDependencies by Delegates.notNull()
}
