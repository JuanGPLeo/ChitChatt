package com.jgpleo.chitchatt.logon.screen.signin.error

import com.jgpleo.chitchatt.logon.error.LogonUiError
import com.jgpleo.chitchatt.logon.ui.R
import com.jgpleo.ui_common.component.dialog.DialogModel

fun getError(error: LogonUiError): DialogModel {
    return when (error) {
        is LogonUiError.Unknown -> DialogModel(
            showDialog = true,
            title = R.string.logon_error_unknown_title,
            description = R.string.logon_error_unknown_description,
            primaryButtonText = R.string.logon_error_unknown_button,
            dismissOnBackPress = true,
            dismissOnClickOut = true
        )

        is LogonUiError.InvalidUser -> DialogModel(
            showDialog = true,
            title = R.string.signin_error_bad_credentials_title,
            description = R.string.signin_error_bad_credentials_description,
            primaryButtonText = R.string.signin_error_bad_credentials_button,
            dismissOnBackPress = true,
            dismissOnClickOut = true
        )

        else -> DialogModel(
            showDialog = true,
            title = R.string.logon_error_unknown_title,
            description = R.string.logon_error_unknown_description,
            primaryButtonText = R.string.logon_error_unknown_button,
            dismissOnBackPress = true,
            dismissOnClickOut = true
        )
    }
}