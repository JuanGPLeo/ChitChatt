package com.jgpleo.chitchatt.logon.domain.model

data class UserData(
    val email: String,
    val name: String
) {
    fun isValid(): Boolean {
        return email.isNotEmpty()
    }
}
