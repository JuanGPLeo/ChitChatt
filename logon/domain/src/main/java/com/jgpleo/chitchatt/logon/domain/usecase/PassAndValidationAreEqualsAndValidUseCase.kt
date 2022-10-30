package com.jgpleo.chitchatt.logon.domain.usecase

import javax.inject.Inject

class PassAndValidationAreEqualsAndValidUseCase @Inject constructor(
    private val isPassValidUseCase: IsPassValidUseCase
) {

    operator fun invoke(pass: String, validatePass: String): PassStatus {
        return if (pass.isEmpty() && validatePass.isEmpty()) {
            PassStatus.BothEmpty
        } else if (pass.isEmpty()) {
            PassStatus.PassEmpty
        } else if (validatePass.isEmpty()) {
            PassStatus.ValidatePassEmpty
        } else if (pass != validatePass) {
            PassStatus.NotEquals
        } else if (isPassValidUseCase(pass, true) is IsPassValidUseCase.PassStatus.WeakPass) {
            PassStatus.WeakPass
        } else {
            PassStatus.Correct
        }
    }

    sealed interface PassStatus {
        object BothEmpty : PassStatus
        object PassEmpty : PassStatus
        object ValidatePassEmpty : PassStatus
        object NotEquals : PassStatus
        object WeakPass : PassStatus
        object Correct : PassStatus
    }
}