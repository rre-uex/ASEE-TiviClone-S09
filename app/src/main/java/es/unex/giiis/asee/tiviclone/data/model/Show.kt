package es.unex.giiis.asee.tiviclone.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Show(
    @PrimaryKey val showId: Int,
    val title: String,
    val description: String,
    val year: String,
    val seasons: String,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean,
    val image: Int,
    val banner: Int,
    @ColumnInfo(name = "image_path") val imagePath: String,
    @ColumnInfo(name = "banner_path") val bannerPath: String,
    val status: String,
) : Serializable
