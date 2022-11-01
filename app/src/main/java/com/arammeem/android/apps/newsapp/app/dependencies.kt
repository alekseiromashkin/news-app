package com.arammeem.android.apps.newsapp.app

import android.content.Context
import androidx.room.Room
import com.arammeem.android.apps.newsapp.core.database.NewsDatabase
import com.arammeem.android.apps.newsapp.core.navigation.SCREEN_ARTICLES
import com.arammeem.android.apps.newsapp.core.navigation.SCREEN_SOURCES
import com.arammeem.android.apps.newsapp.core.navigation.ScreenProvider
import com.arammeem.android.apps.newsapp.core.navigation.ScreenProviderImpl
import com.arammeem.android.apps.newsapp.feature.articles.presentation.ArticlesFragment
import com.arammeem.android.apps.newsapp.feature.sources.presentation.SourcesFragment
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit

internal fun buildScreenProvider(): ScreenProvider {
    return ScreenProviderImpl()
        .registerScreen(SCREEN_SOURCES, SourcesFragment::class)
        .registerScreen(SCREEN_ARTICLES, ArticlesFragment::class)
}

@OptIn(ExperimentalSerializationApi::class)
internal fun buildRetrofit(): Retrofit {
    val contentType = "application/json".toMediaType()
    val okhttpClient =  OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("apiKey", "1ae07cf227dd4700a3a3afecd864e4ab")
                .build()

            val requestBuilder = original.newBuilder().url(url)
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply { setLevel(Level.BODY) }
        )
        .build()
    return Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .addConverterFactory(Json.asConverterFactory(contentType))
        .client(okhttpClient)
        .build()
}

internal fun buildDatabase(applicationContext: Context): NewsDatabase {
    return Room.databaseBuilder(
        applicationContext,
        NewsDatabase::class.java, "news.db"
    ).build()
}
