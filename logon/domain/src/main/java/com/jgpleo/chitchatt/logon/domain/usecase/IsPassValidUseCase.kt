package com.jgpleo.chitchatt.logon.domain.usecase

import java.util.regex.Pattern
import javax.inject.Inject

class IsPassValidUseCase @Inject constructor() {

    private val passPattern = Pattern.compile(
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$"
    )

    operator fun invoke(pass: String, validatePattern: Boolean = false): PassStatus {
        return if (pass.isEmpty()) {
            PassStatus.Empty
        } else if (validatePattern && !passPattern.matcher(pass).matches()) {
            PassStatus.WeakPass
        } else {
            PassStatus.Correct
        }
    }

    sealed interface PassStatus {
        object Empty : PassStatus
        object WeakPass : PassStatus
        object Correct : PassStatus
    }

}