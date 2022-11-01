package com.arammeem.android.apps.newsapp.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arammeem.android.apps.newsapp.core.database.model.SourcePersistentEntity

@Dao
interface SourceDao {
    @Query("SELECT * FROM source")
    fun getSources(): List<SourcePersistentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sources: List<SourcePersistentEntity>)

    @Delete
    fun delete(source: SourcePersistentEntity)
}
