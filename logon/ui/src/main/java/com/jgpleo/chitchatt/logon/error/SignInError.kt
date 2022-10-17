package com.jgpleo.chitchatt.logon.error

sealed interface SignInError : LogonUiError {
    data class MandatoryFields(val isEmailEmpty: Boolean, val isPassEmpty: Boolean) : SignInError
    object InvalidEmailFormat: SignInError
}