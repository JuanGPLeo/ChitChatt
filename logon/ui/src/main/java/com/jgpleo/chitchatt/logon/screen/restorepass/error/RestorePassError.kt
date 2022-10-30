package com.jgpleo.chitchatt.logon.screen.restorepass.error

import com.jgpleo.chitchatt.logon.domain.error.LogonError
import com.jgpleo.chitchatt.logon.ui.R
import com.jgpleo.domain_common.usecase.result.DomainError
import com.jgpleo.ui_common.component.dialog.DialogModel

fun getRestorePassError(error: DomainError): DialogModel {
    return when (error) {
        is LogonError.Unknown -> DialogModel(
            showDialog = true,
            title = R.string.logon_error_unknown_title,
            description = R.string.logon_error_unknown_description,
            primaryButtonText = R.string.logon_error_unknown_button,
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