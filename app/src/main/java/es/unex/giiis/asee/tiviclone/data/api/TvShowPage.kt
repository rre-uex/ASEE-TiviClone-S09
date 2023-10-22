package es.unex.giiis.asee.tiviclone.data.api

import com.google.gson.annotations.SerializedName

data class TvShowPage(
    @SerializedName("total"    ) var total   : String?            = null,
    @SerializedName("page"     ) var page    : Int?               = null,
    @SerializedName("pages"    ) var pages   : Int?               = null,
    @SerializedName("tv_shows" ) var tvShows : ArrayList<TvShow> = arrayListOf()
)
