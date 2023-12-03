package es.unex.giiis.asee.tiviclone.util

import android.content.Context
import es.unex.giiis.asee.tiviclone.api.getNetworkService
import es.unex.giiis.asee.tiviclone.data.Repository
import es.unex.giiis.asee.tiviclone.database.TiviCloneDatabase

class AppContainer(context: Context?) {

    private val networkService = getNetworkService()
    private val db = TiviCloneDatabase.getInstance(context!!)
    val repository = Repository(db!!.userDao(),db.showDao(),getNetworkService())

}