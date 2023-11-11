package es.unex.giiis.asee.tiviclone.data.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["userId", "showId"],
    foreignKeys = [
        ForeignKey(
            entity = Show::class,
            parentColumns = ["showId"],
            childColumns = ["showId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserShowCrossRef(
    val userId: Long,
    val showId: Int
)
