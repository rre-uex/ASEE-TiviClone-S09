package es.unex.giiis.asee.tiviclone.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithShows(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "showId",
        associateBy = Junction(UserShowCrossRef::class)
    )
    val shows: List<Show>
)

