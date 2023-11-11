package es.unex.giiis.asee.tiviclone.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.unex.giiis.asee.tiviclone.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE name LIKE :first LIMIT 1")
    suspend fun findByName(first: String): User

    @Insert
    suspend fun insert(user: User): Long

}
