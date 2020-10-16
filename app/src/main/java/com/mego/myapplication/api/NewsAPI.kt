package com.mego.myapplication.api

import androidx.lifecycle.LiveData
import com.mego.myapplication.models.News
import com.mego.myapplication.models.NewsResponse
import com.mego.myapplication.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsAPI {
    @GET("/svc/topstories/v2/{section}.json")
   suspend fun getStories(@Path("section") section: String,
                   @Query("api-key") apiKey : String = API_KEY
                  )
            : Response<NewsResponse>
}