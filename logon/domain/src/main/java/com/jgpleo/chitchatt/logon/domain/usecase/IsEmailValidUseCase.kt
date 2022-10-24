package com.jgpleo.chitchatt.logon.domain.usecase

import java.util.regex.Pattern
import javax.inject.Inject

class IsEmailValidUseCase @Inject constructor() {

    private val emailPattern = Pattern.compile(
        "^([A-Za-z](.*)){2,}([@])(.+)(\\.)(.+)"
    )

    operator fun invoke(email: String): EmailStatus {
        return if (email.isEmpty()) {
            EmailStatus.Empty
        } else if (!emailPattern.matcher(email).matches()) {
            EmailStatus.InvalidFormat
        } else {
            EmailStatus.Correct
        }
    }

    sealed interface EmailStatus {
        object Empty : EmailStatus
        object InvalidFormat: EmailStatus
        object Correct : EmailStatus
    }

}