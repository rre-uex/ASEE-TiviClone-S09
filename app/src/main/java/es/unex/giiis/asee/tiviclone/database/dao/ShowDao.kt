package es.unex.giiis.asee.tiviclone.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import es.unex.giiis.asee.tiviclone.data.model.Show
import es.unex.giiis.asee.tiviclone.data.model.UserShowCrossRef
import es.unex.giiis.asee.tiviclone.data.model.UserWithShows

@Dao
interface ShowDao {

    @Query("SELECT * FROM show WHERE showId = :id")
    suspend fun findById(id: Int): Show

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(show: Show)

    @Delete
    suspend fun delete(show: Show)

    @Transaction
    @Query("SELECT * FROM User where userId = :userId")
    suspend fun getUserWithShows(userId: Long): UserWithShows

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserShow(crossRef: UserShowCrossRef)

    @Transaction
    suspend fun insertAndRelate(show: Show, userId: Long) {
        insert(show)
        insertUserShow(UserShowCrossRef(userId, show.showId))
    }
}