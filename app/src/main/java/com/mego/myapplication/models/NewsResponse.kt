package com.mego.myapplication.models
/*Response Model*/
data class NewsResponse(val status: String = "",
                        val section: String = "",
                        val totalResults: Int = 0,
                        val results: MutableList<News> = mutableListOf() ) {
    companion object {
        val STATUS_OK = "OK"
    }
}