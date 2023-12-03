package es.unex.giiis.asee.tiviclone.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.unex.giiis.asee.tiviclone.data.model.Show
import es.unex.giiis.asee.tiviclone.data.model.User

class HomeViewModel: ViewModel() {
    private val _user = MutableLiveData<User>(null)
    val user: LiveData<User>
        get() = _user

    var userInSession: User? = null
        set(value) {
            field = value
            _user.value = value!!
        }

    private val _navigateToShow = MutableLiveData<Show>(null)
    val navigateToShow: LiveData<Show>
        get() = _navigateToShow

    fun onShowClick(show: Show) {
        _navigateToShow.value = show
    }

}