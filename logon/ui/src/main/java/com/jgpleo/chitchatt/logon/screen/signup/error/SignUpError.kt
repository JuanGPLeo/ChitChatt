package com.jgpleo.chitchatt.logon.screen.signup.error

import com.jgpleo.chitchatt.logon.domain.error.LogonError
import com.jgpleo.chitchatt.logon.ui.R
import com.jgpleo.domain_common.usecase.result.DomainError
import com.jgpleo.ui_common.component.dialog.DialogModel

fun getSignUpError(error: DomainError): DialogModel {
    return when (error) {
        is LogonError.Unknown -> DialogModel(
            showDialog = true,
            title = R.string.logon_error_unknown_title,
            description = R.string.logon_error_unknown_description,
            primaryButtonText = R.string.logon_error_unknown_button,
            dismissOnBackPress = true,
            dismissOnClickOut = true
        )

        is LogonError.WeakPass -> DialogModel(
            showDialog = true,
            title = R.string.signup_error_weak_pass_title,
            description = R.string.signup_error_weak_pass_description,
            primaryButtonText = R.string.signup_error_weak_pass_button,
            dismissOnBackPress = true,
            dismissOnClickOut = true
        )

        is LogonError.InvalidUserData -> DialogModel(
            showDialog = true,
            title = R.string.signin_error_invalid_user_data_title,
            description = R.string.signin_error_invalid_user_data_description,
            primaryButtonText = R.string.signin_error_invalid_user_data_button,
            dismissOnBackPress = true,
            dismissOnClickOut = true
        )

        is LogonError.UserAlreadyExists -> DialogModel(
            showDialog = true,
            title = R.string.signup_error_user_already_exists_title,
            description = R.string.signup_error_user_already_exists_description,
            primaryButtonText = R.string.signup_error_user_already_exists_button,
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