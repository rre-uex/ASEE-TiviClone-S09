package es.unex.giiis.asee.tiviclone.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.giiis.asee.tiviclone.TiviCloneApplication
import es.unex.giiis.asee.tiviclone.api.APIError
import es.unex.giiis.asee.tiviclone.data.Repository
import es.unex.giiis.asee.tiviclone.data.model.Show
import es.unex.giiis.asee.tiviclone.data.model.User
import es.unex.giiis.asee.tiviclone.data.toShow
import kotlinx.coroutines.launch

class ShowDetailViewModel (
    private val repository: Repository
) : ViewModel() {

    private val _showDetail = MutableLiveData<Show>(null)
    val showDetail: LiveData<Show>
        get() = _showDetail

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    var user: User? = null
    var show: Show? = null
        set(value) {
            field = value
            getShow()
        }

    private fun getShow() {
        if (show!=null)
            viewModelScope.launch{
                try{
                    val _show = repository.fetchShowDetail(show!!.showId).toShow()
                    _show.isFavorite = show!!.isFavorite
                    _showDetail.value = _show
                } catch (error: APIError) {
                    _toast.value = error.message
                }
            }
    }

    fun setFavorite(show: Show){
        viewModelScope.launch {
            show.isFavorite = true
            repository.showToLibrary(show,user!!.userId!!)
        }
    }

    fun setNoFavorite(show: Show){
        viewModelScope.launch {
            show.isFavorite = false
            repository.deleteShowFromLibrary(show,user!!.userId!!)
        }
    }

    fun onToastShown() {
        _toast.value = null
    }


    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])

                return ShowDetailViewModel(
                    (application as TiviCloneApplication).appContainer.repository,

                    ) as T
            }
        }
    }
}
