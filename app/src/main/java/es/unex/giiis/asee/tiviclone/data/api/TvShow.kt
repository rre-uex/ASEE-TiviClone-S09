package es.unex.giiis.asee.tiviclone.data.api

import com.google.gson.annotations.SerializedName

data class TvShow (

    @SerializedName("id"                   ) var id                 : Int?                = null,
    @SerializedName("name"                 ) var name               : String?             = null,
    @SerializedName("permalink"            ) var permalink          : String?             = null,
    @SerializedName("url"                  ) var url                : String?             = null,
    @SerializedName("description"          ) var description        : String?             = null,
    @SerializedName("description_source"   ) var descriptionSource  : String?             = null,
    @SerializedName("start_date"           ) var startDate          : String?             = null,
    @SerializedName("end_date"             ) var endDate            : String?             = null,
    @SerializedName("country"              ) var country            : String?             = null,
    @SerializedName("status"               ) var status             : String?             = null,
    @SerializedName("runtime"              ) var runtime            : Int?                = null,
    @SerializedName("network"              ) var network            : String?             = null,
    @SerializedName("youtube_link"         ) var youtubeLink        : String?             = null,
    @SerializedName("image_path"           ) var imagePath          : String?             = null,
    @SerializedName("image_thumbnail_path" ) var imageThumbnailPath : String?             = null,
    @SerializedName("rating"               ) var rating             : String?             = null,
    @SerializedName("rating_count"         ) var ratingCount        : String?             = null,
    @SerializedName("countdown"            ) var countdown          : String?             = null,
    @SerializedName("genres"               ) var genres             : ArrayList<String>   = arrayListOf(),
    @SerializedName("pictures"             ) var pictures           : ArrayList<String>   = arrayListOf(),
    @SerializedName("episodes"             ) var episodes           : ArrayList<TvShowEpisode> = arrayListOf()

)
