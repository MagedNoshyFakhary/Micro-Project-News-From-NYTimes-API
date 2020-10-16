package com.mego.myapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
/*model & Table*/
@Entity(tableName = "news")
data class News(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var url: String?,
    var title: String?,
    var published_date: String?,
    var multimedia: List<Multimedia>?): Serializable
    {
        constructor() : this(0,"","","", listOf())

        data class Multimedia(val url: String?,
                              val format: String?,
                              val type: String?,
                              val subtype: String?,
                              val caption: String?,
                              val copyright: String?,
                              val height: Int = 0,
                              val width: Int = 0)

    }
