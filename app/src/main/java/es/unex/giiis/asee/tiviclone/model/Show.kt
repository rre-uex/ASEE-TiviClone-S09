package es.unex.giiis.asee.tiviclone.model

import java.io.Serializable

data class Show(
    val title: String,
    val description: String,
    val year: String,
    val seasons: String,
    var isFavorite: Boolean,
    val image: Int,
    val banner: Int,
    val genres: List<String> = emptyList()
) : Serializable
