package es.unex.giiis.asee.tiviclone.view.home

import es.unex.giiis.asee.tiviclone.data.model.User

interface UserProvider {
    fun getUser(): User
}