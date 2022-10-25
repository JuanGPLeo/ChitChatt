package com.jgpleo.chitchatt.logon.screen.signin.error

import com.jgpleo.chitchatt.logon.domain.usecase.IsEmailValidUseCase.EmailStatus
import com.jgpleo.chitchatt.logon.domain.usecase.IsPassValidUseCase.PassStatus
import com.jgpleo.chitchatt.logon.ui.R
import com.jgpleo.ui_common.component.textfield.TextFieldError

fun checkEmailStatus(emailStatus: EmailStatus): TextFieldError {
    return when (emailStatus) {
        is EmailStatus.Empty -> TextFieldError(
            hasError = true,
            errorMessage = R.string.signin_error_mandatory_email
        )

        is EmailStatus.InvalidFormat -> TextFieldError(
            hasError = true,
            errorMessage = R.string.signin_error_invalid_email
        )

        is EmailStatus.Correct -> TextFieldError()
    }
}

fun checkPassStatus(passStatus: PassStatus): TextFieldError {
    return when (passStatus) {
        is PassStatus.Empty -> TextFieldError(
            hasError = true,
            errorMessage = R.string.signin_error_mandatory_pass
        )

        is PassStatus.Correct -> TextFieldError()

        else -> TextFieldError()
    }
}