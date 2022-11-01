package com.arammeem.android.apps.newsapp.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "source")
data class SourcePersistentEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "category") val category: String?,
    @ColumnInfo(name = "language") val language: String?,
    @ColumnInfo(name = "country") val country: String?,
)
