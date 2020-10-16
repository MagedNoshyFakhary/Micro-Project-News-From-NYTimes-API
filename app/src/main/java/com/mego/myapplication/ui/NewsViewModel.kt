package com.mego.myapplication.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mego.myapplication.models.News
import com.mego.myapplication.models.NewsResponse
import com.mego.myapplication.repository.NewsRepository
import com.mego.myapplication.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel() {

    val topStories: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var topStoriesPage = 1
    var topStoriesResponse: NewsResponse? = null

/*Constructor*/
    init {
        getTopStories("home")
    }
/*getTopStories*/
    fun getTopStories(section: String) = viewModelScope.launch {
        topStories.postValue(Resource.Loading())
        val response = newsRepository.getTopStories(section)
        topStories.postValue(handleTopStoriesResponse(response))
    }


    private fun handleTopStoriesResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                topStoriesPage++
                if(topStoriesResponse == null) {
                    topStoriesResponse = resultResponse
                } else {
                    val oldArticles = topStoriesResponse?.results
                    val newArticles = resultResponse.results
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(topStoriesResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

/*Save Article to Room Db*/
    fun saveArticle(news: News) = viewModelScope.launch {
        newsRepository.upsert(news)
    }
/*Show Articles from Room Db*/
    fun getSavedNews() = newsRepository.getSavedNews()
/*Delete Articles from Room Db*/
    fun deleteArticle(news: News) = viewModelScope.launch {
        newsRepository.deleteArticle(news)
    }
}