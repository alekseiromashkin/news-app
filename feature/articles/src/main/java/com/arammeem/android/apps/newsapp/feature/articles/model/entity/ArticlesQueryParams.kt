package com.arammeem.android.apps.newsapp.feature.articles.model.entity

import android.os.Bundle
import com.arammeem.android.apps.newsapp.core.navigation.SCREEN_ARGUMENT_SOURCE

internal data class ArticlesQueryParams(
    val country: String? = null,
    val category: String? = null,
    val source: String? = null,
    val q: String? = null,
)

internal fun Bundle?.toArticlesQueryParams(): ArticlesQueryParams {
    return ArticlesQueryParams(
        source = this?.getString(SCREEN_ARGUMENT_SOURCE)
    )
}
