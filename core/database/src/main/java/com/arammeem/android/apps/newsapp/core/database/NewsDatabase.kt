package com.arammeem.android.apps.newsapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arammeem.android.apps.newsapp.core.database.model.ArticlePersistentEntity
import com.arammeem.android.apps.newsapp.core.database.model.SourcePersistentEntity

@Database(entities = [SourcePersistentEntity::class, ArticlePersistentEntity::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun sourceDao(): SourceDao
    abstract fun articleDao(): ArticleDao
}

