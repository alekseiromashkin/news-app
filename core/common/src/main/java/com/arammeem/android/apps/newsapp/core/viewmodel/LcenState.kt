package com.arammeem.android.apps.newsapp.core.viewmodel

sealed class LcenState<out T> {
    object Loading: LcenState<Nothing>()
    class Content<T>(val value: T?): LcenState<Nothing>()
    class Error(val throwable: Throwable?): LcenState<Nothing>()
    object None : LcenState<Nothing>()
}

inline fun <reified T> LcenState<T?>.asContent(block: T?.() -> Unit): LcenState<T?> {
    if (this is LcenState.Content<*>) {
        block(this.value as T)
    }
    return this
}

inline fun <reified T> LcenState<T?>.asError(block: Throwable?.() -> Unit): LcenState<T?> {
    if (this is LcenState.Error) {
        block(this.throwable)
    }
    return this
}

fun <T> Result<T>.toLcen(): LcenState<T> {
    return if(this.isSuccess) {
        LcenState.Content(getOrNull())
    } else {
        LcenState.Error(exceptionOrNull())
    }
}