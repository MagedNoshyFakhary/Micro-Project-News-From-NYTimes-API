package com.mego.myapplication.repository

import com.mego.myapplication.api.RetrofitBluider
import com.mego.myapplication.db.NewsDB
import com.mego.myapplication.models.News
/*Repository Class*/
class NewsRepository(
    val db: NewsDB
){
suspend fun getTopStories(section: String) =
    RetrofitBluider.api.getStories(section)
    suspend fun upsert(news:News) = db.getArticleDao().upsert(news)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(news:News) = db.getArticleDao().deleteArticle(news)
}