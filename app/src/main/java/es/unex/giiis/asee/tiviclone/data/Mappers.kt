package es.unex.giiis.asee.tiviclone.data

import es.unex.giiis.asee.tiviclone.R
import es.unex.giiis.asee.tiviclone.data.api.TvShow
import es.unex.giiis.asee.tiviclone.data.model.Show

fun TvShow.toShow() = Show(
    id = id ?: 0,
    title = name ?: "",
    description = description ?: "",
    year = startDate?.substring(0, 4) ?: "",
    seasons = "",
    isFavorite = false,
    image = R.drawable.the_wire,
    banner = R.drawable.the_wire_banner,
    status = status ?: "",
    imagePath = imageThumbnailPath ?: "",
    bannerPath = imagePath ?: ""

)

fun Show.toTvShow() = TvShow(
    id = id,
    name = title,
    description = description,
    startDate = year,
    status = status,
    imageThumbnailPath = imagePath
)