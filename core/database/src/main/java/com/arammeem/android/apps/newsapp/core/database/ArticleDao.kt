package com.arammeem.android.apps.newsapp.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arammeem.android.apps.newsapp.core.database.model.ArticlePersistentEntity

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article WHERE sourceId IN (:sources) ORDER BY publishedAt DESC LIMIT :limit OFFSET :offset")
    fun getArticles(sources: List<String>, limit: Int, offset: Int): List<ArticlePersistentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articles: List<ArticlePersistentEntity>)

    @Delete
    fun delete(article: ArticlePersistentEntity)
}
