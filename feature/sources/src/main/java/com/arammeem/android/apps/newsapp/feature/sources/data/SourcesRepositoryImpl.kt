package com.arammeem.android.apps.newsapp.feature.sources.data

import com.arammeem.android.apps.newsapp.core.errors.ServerException
import com.arammeem.android.apps.newsapp.core.network.ErrorBody
import com.arammeem.android.apps.newsapp.feature.sources.data.entity.Source
import com.arammeem.android.apps.newsapp.feature.sources.data.network.SourcesApi
import com.arammeem.android.apps.newsapp.feature.sources.data.network.toSourceList
import com.arammeem.android.apps.newsapp.feature.sources.model.SourcesRepository
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import okhttp3.ResponseBody
import javax.inject.Inject

internal class SourcesRepositoryImpl @Inject constructor(
    private val sourcesApi: SourcesApi
) : SourcesRepository {

    override suspend fun getSources(
        category: String?,
        language: String?,
        country: String?
    ): List<Source> {
        val result = sourcesApi.getSources(category, language, country)
        return if (result.isSuccessful) {
            result.body()?.toSourceList().orEmpty()
        } else {
            val error = result.errorBody()?.toErrorBody()
            throw ServerException(error?.code, error?.message)
        }
    }
}

@OptIn(ExperimentalSerializationApi::class)
private fun ResponseBody?.toErrorBody(): ErrorBody {
    if (this == null) {
        return ErrorBody()
    }
    return Json.decodeFromStream(ErrorBody.serializer() ,byteStream())
}
