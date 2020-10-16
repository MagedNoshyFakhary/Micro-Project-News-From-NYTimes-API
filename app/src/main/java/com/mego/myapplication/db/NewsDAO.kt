package com.mego.myapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mego.myapplication.models.News

@Dao
interface NewsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: News): Long

    @Query("SELECT * FROM news")
    fun getAllArticles(): LiveData<List<News>>

    @Delete
    suspend fun deleteArticle(article: News)
}
