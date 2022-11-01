package com.arammeem.android.apps.newsapp.feature.sources.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface SourcesApi {

    @GET("v2/top-headlines/sources")
    suspend fun getSources(
        @Query("category") category: String?,
        @Query("language") language: String?,
        @Query("country") country: String?,
    ) : Response<SourcesResponse>
}
