package com.arammeem.android.apps.newsapp.core.network

import kotlinx.serialization.Serializable

@Serializable
data class ErrorBody(
    val status: String = "",
    val code: String = "",
    val message: String = "",
)
