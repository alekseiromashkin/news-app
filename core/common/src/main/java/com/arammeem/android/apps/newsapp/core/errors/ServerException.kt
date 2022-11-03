package com.arammeem.android.apps.newsapp.core.errors

class ServerException(
    val code: String? = null,
    override val message: String? = null,
) : Throwable()