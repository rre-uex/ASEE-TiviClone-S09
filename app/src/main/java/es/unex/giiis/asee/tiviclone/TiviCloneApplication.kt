package es.unex.giiis.asee.tiviclone

import android.app.Application
import es.unex.giiis.asee.tiviclone.util.AppContainer

class TiviCloneApplication: Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}