package com.jgpleo.chitchatt.logon.error

sealed interface SignUpError : LogonUiError {
    object PassNotEquals : SignUpError
}