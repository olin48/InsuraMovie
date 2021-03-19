package com.panelic.insuramovie.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GenresResult {
    @SerializedName("genres")
    @Expose
    val genres: List<Genres>? = null
}

class Genres {
    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("name")
    @Expose
    val name: String? = null
}