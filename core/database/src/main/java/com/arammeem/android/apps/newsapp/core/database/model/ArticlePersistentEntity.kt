package com.arammeem.android.apps.newsapp.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "article",
    primaryKeys = ["sourceId", "title", "publishedAt"]
)
data class ArticlePersistentEntity(
    @ColumnInfo(name = "sourceId") val sourceId: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "publishedAt") val publishedAt: Long,
    @ColumnInfo(name = "source_name") val sourceName: String,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "urlToImage") val urlToImage: String?,
    @ColumnInfo(name = "content") val content: String?,
)
