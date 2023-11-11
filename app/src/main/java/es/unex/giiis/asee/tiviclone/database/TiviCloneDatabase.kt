package es.unex.giiis.asee.tiviclone.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.unex.giiis.asee.tiviclone.data.model.Show
import es.unex.giiis.asee.tiviclone.data.model.User
import es.unex.giiis.asee.tiviclone.data.model.UserShowCrossRef
import es.unex.giiis.asee.tiviclone.database.dao.ShowDao
import es.unex.giiis.asee.tiviclone.database.dao.UserDao

@Database(entities = [User::class, Show::class, UserShowCrossRef::class], version = 1)
abstract class TiviCloneDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun showDao(): ShowDao

    companion object {
        private var INSTANCE: TiviCloneDatabase? = null

        fun getInstance(context: Context): TiviCloneDatabase? {
            if (INSTANCE == null) {
                synchronized(TiviCloneDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        TiviCloneDatabase::class.java, "tiviclone.db"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
