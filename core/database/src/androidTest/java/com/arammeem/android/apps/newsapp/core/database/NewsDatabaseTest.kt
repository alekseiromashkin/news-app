package com.arammeem.android.apps.newsapp.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arammeem.android.apps.newsapp.core.database.model.ArticlePersistentEntity
import com.arammeem.android.apps.newsapp.core.database.model.SourcePersistentEntity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {

    private lateinit var sourceDao: SourceDao
    private lateinit var articleDao: ArticleDao
    private lateinit var db: NewsDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, NewsDatabase::class.java
        ).build()
        sourceDao = db.sourceDao()
        articleDao = db.articleDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun sourceInsertReadDelete() {
        val insertSource = SourcePersistentEntity(
            id = "1",
            name = "New Your Times",
            description = "This is the description",
            url = "https://www.nytimes.com/",
            category = "general",
            language = "en",
            country = "us",
        )

        sourceDao.insert(listOf(insertSource))
        val readSource = sourceDao.getSources().first()
        assertEquals(insertSource, readSource)

        sourceDao.delete(insertSource)
        val result = sourceDao.getSources()
        assertTrue(result.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun articleInsertReadDelete() {
        val insertArticle1 = ArticlePersistentEntity(
            sourceId = "1",
            title = "Title 1",
            publishedAt = 0,
            sourceName = "New York Times",
            author = "Stephen King",
            description = "Description 1",
            url = "http://newssource.io",
            urlToImage = "",
            content = "Content 1",
        )

        val insertArticle2 = ArticlePersistentEntity(
            sourceId = "2",
            title = "Title 2",
            publishedAt = 0,
            sourceName = "CNN",
            author = "Mark Twain",
            description = "Description 2",
            url = "http://newssource.io",
            urlToImage = "",
            content = "Content 2",
        )

        articleDao.insert(listOf(insertArticle1, insertArticle2))

        val listArticle = articleDao.getArticles(listOf("2"), 10, 0)
        assertTrue(listArticle.size == 1)

        val readArticle = listArticle.first()
        assertEquals(readArticle, insertArticle2)

        articleDao.delete(insertArticle2)
        val result = articleDao.getArticles(listOf("2"), 10, 0)
        assertTrue(result.isEmpty())
    }
}
