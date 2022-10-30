package com.jgpleo.chitchatt.logon.domain.usecase

import com.jgpleo.chitchatt.logon.domain.error.LogonError
import com.jgpleo.chitchatt.logon.domain.model.UserCredentials
import com.jgpleo.chitchatt.logon.domain.model.UserData
import com.jgpleo.chitchatt.logon.domain.repository.LogonRepository
import com.jgpleo.chitchatt.logon.domain.repository.error.LogonRepositoryError
import com.jgpleo.domain_common.usecase.UseCase
import com.jgpleo.domain_common.usecase.dispatcher.DispatcherProvider
import com.jgpleo.domain_common.usecase.result.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val repository: LogonRepository
) : UseCase<UserCredentials, UserData>(dispatcherProvider) {

    override fun prepareFlow(input: UserCredentials): Flow<Either<UserData, DomainError>> =
        flow {
            repository.signIn(input)
                .onSuccess {
                    if (it.isValid()) {
                        emit(eitherSuccess(it))
                    } else {
                        emit(eitherFailure(LogonError.InvalidUser))
                    }
                }
                .onFailure { error ->
                    val result = when (error) {
                        is LogonRepositoryError.InvalidUser -> LogonError.InvalidUser
                        is LogonRepositoryError.InvalidUserData -> LogonError.InvalidUserData
                        is LogonRepositoryError.Unknown -> LogonError.Unknown
                        else -> LogonError.Unknown
                    }
                    emit(eitherFailure(result))
                }
        }

}