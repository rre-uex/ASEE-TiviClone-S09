package es.unex.giiis.asee.tiviclones02.model

import java.io.Serializable

data class User(
    val name: String = "",
    val password: String = ""
) : Serializable
