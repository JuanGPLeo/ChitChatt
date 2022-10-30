package com.jgpleo.chitchatt.logon.screen.signup.error

import com.jgpleo.chitchatt.logon.domain.usecase.PassAndValidationAreEqualsAndValidUseCase.PassStatus
import com.jgpleo.chitchatt.logon.ui.R
import com.jgpleo.ui_common.component.textfield.TextFieldError

fun checkPassStatus(passStatus: PassStatus): TextFieldError {
    return when (passStatus) {
        is PassStatus.BothEmpty -> TextFieldError(
            hasError = true,
            errorMessage = R.string.signup_error_mandatory_pass
        )

        is PassStatus.PassEmpty -> TextFieldError(
            hasError = true,
            errorMessage = R.string.signup_error_mandatory_pass
        )

        is PassStatus.ValidatePassEmpty -> TextFieldError(
            hasError = true,
            errorMessage = R.string.signup_error_mandatory_validate_pass
        )

        is PassStatus.NotEquals -> TextFieldError(
            hasError = true,
            errorMessage = R.string.signup_error_both_pass_not_equals
        )

        is PassStatus.WeakPass -> TextFieldError(
            hasError = true,
            errorMessage = R.string.signup_error_weak_pass_field
        )

        else -> TextFieldError()
    }
}