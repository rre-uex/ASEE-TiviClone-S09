package es.unex.giiis.asee.tiviclone.data.api

import com.google.gson.annotations.SerializedName

data class TvShowEpisode(
    @SerializedName("season"   ) var season  : Int?    = null,
    @SerializedName("episode"  ) var episode : Int?    = null,
    @SerializedName("name"     ) var name    : String? = null,
    @SerializedName("air_date" ) var airDate : String? = null
)
