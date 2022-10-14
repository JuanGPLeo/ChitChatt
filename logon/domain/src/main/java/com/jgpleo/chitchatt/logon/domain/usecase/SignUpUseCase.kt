package com.jgpleo.chitchatt.logon.domain.usecase

import com.jgpleo.chitchatt.logon.domain.error.LogonError
import com.jgpleo.chitchatt.logon.domain.model.UserCredentials
import com.jgpleo.chitchatt.logon.domain.repository.LogonRepository
import com.jgpleo.domain_common.usecase.UseCase
import com.jgpleo.domain_common.usecase.dispatcher.DispatcherProvider
import com.jgpleo.domain_common.usecase.result.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val repository: LogonRepository
) : UseCase<UserCredentials>(dispatcherProvider) {

    override fun prepareFlow(input: UserCredentials): Flow<Either<DomainSuccess, DomainError>> =
        flow {
            repository.signUp(input)
                .onSuccess {
                    if (it.isValid()) {
                        emit(eitherSuccess(DomainSuccess.Result(it)))
                    } else {
                        emit(eitherFailure(LogonError.InvalidUser))
                    }
                }
                .onFailure {
                    emit(eitherFailure(LogonError.InvalidUser))
                }
        }

}