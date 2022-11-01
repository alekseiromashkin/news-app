package com.arammeem.android.apps.newsapp.feature.articles.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ArticlesApi {

    @GET("v2/top-headlines")
    suspend fun getArticles(
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("sources") sources: String?,
        @Query("q") q: String?,
        @Query("pageSize") pageSize: Int?, // 20 is the default, 100 is the maximum
        @Query("page") page: Int?,
    ) : Response<ArticlesResponse>
}
