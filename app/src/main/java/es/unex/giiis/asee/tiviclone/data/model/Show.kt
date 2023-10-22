package es.unex.giiis.asee.tiviclone.data.model

import java.io.Serializable

data class Show(
    val id: Int,
    val title: String,
    val description: String,
    val year: String,
    val seasons: String,
    var isFavorite: Boolean,
    val image: Int,
    val banner: Int,
    val imagePath: String,
    val bannerPath: String,
    val status: String,
    val genres: List<String> = emptyList()
) : Serializable
