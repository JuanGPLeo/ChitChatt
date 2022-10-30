package com.jgpleo.chitchatt.logon.screen.common.error

import com.jgpleo.chitchatt.logon.domain.usecase.IsEmailValidUseCase.EmailStatus
import com.jgpleo.chitchatt.logon.ui.R
import com.jgpleo.ui_common.component.textfield.TextFieldError

fun checkEmailStatus(emailStatus: EmailStatus): TextFieldError {
    return when (emailStatus) {
        is EmailStatus.Empty -> TextFieldError(
            hasError = true,
            errorMessage = R.string.logon_error_mandatory_email
        )

        is EmailStatus.InvalidFormat -> TextFieldError(
            hasError = true,
            errorMessage = R.string.signin_error_invalid_email
        )

        is EmailStatus.Correct -> TextFieldError()
    }
}