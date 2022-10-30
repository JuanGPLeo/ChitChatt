package com.jgpleo.chitchatt.logon.domain.usecase

import com.jgpleo.chitchatt.logon.domain.error.LogonError
import com.jgpleo.chitchatt.logon.domain.model.EmailRestorePass
import com.jgpleo.chitchatt.logon.domain.repository.LogonRepository
import com.jgpleo.chitchatt.logon.domain.repository.error.LogonRepositoryError
import com.jgpleo.domain_common.usecase.UseCase
import com.jgpleo.domain_common.usecase.dispatcher.DispatcherProvider
import com.jgpleo.domain_common.usecase.result.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RestorePassUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val repository: LogonRepository
) : UseCase<EmailRestorePass, DomainSuccess.EmptyResult>(dispatcherProvider) {

    override fun prepareFlow(
        input: EmailRestorePass
    ): Flow<Either<DomainSuccess.EmptyResult, DomainError>> = flow {
        repository.restorePass(input)
            .onSuccess { emit(eitherSuccessEmpty()) }
            .onFailure { error ->
                val result = when (error) {
                    is LogonRepositoryError.InvalidUser -> LogonError.InvalidUser
                    is LogonRepositoryError.Unknown -> LogonError.Unknown
                    else -> LogonError.Unknown
                }
                emit(eitherFailure(result))
            }
    }

}