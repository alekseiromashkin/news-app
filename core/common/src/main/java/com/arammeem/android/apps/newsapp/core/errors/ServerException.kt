package com.arammeem.android.apps.newsapp.core.errors

class ServerException(
    val code: String?,
    override val message: String?,
) : Throwable()