package com.arammeem.android.apps.newsapp.feature.sources.data.entity

internal data class Source(
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: Category,
    val language: String,
    val country: String,
)

internal enum class Category {
    BUSINESS,
    ENTERTAINMENT,
    GENERAL,
    HEALTH,
    SCIENCE,
    SPORTS,
    TECHNOLOGY,
}
