package es.unex.giiis.asee.tiviclone.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.asee.tiviclone.TiviCloneApplication
import es.unex.giiis.asee.tiviclone.data.Repository
import es.unex.giiis.asee.tiviclone.data.model.Show
import es.unex.giiis.asee.tiviclone.data.model.User
import kotlinx.coroutines.launch

class LibraryViewModel  (
    private val repository: Repository
) : ViewModel() {

    val showsInLibrary = repository.showsInLibrary

    var user: User? = null
        set(value) {
            field = value
            repository.setUserid(value!!.userId!!)
        }

    fun setNoFavorite(show: Show){
        viewModelScope.launch {
            show.isFavorite = false
            repository.deleteShowFromLibrary(show,user!!.userId!!)
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return LibraryViewModel(
                    (application as TiviCloneApplication).appContainer.repository,

                    ) as T
            }
        }
    }
}