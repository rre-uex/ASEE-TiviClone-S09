package es.unex.giiis.asee.tiviclone.data.api

import com.google.gson.annotations.SerializedName

data class TvShowDetail (

    @SerializedName("tvShow" ) var tvShow : TvShow? = TvShow()

)
