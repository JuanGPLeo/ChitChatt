package com.jgpleo.chitchatt.logon.data.repository

import com.jgpleo.chitchatt.logon.data.mapper.UserMapper
import com.jgpleo.chitchatt.logon.data.source.FailureState
import com.jgpleo.chitchatt.logon.data.source.RemoteDataSource
import com.jgpleo.chitchatt.logon.data.source.State
import com.jgpleo.chitchatt.logon.domain.model.EmailRestorePass
import com.jgpleo.chitchatt.logon.domain.model.UserCredentials
import com.jgpleo.chitchatt.logon.domain.model.UserData
import com.jgpleo.chitchatt.logon.domain.repository.LogonRepository
import com.jgpleo.chitchatt.logon.domain.repository.error.LogonRepositoryError
import com.jgpleo.domain_common.usecase.repository.RepositoryError
import com.jgpleo.domain_common.usecase.result.Either
import com.jgpleo.domain_common.usecase.result.eitherFailure
import com.jgpleo.domain_common.usecase.result.eitherSuccess
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class LogonRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : LogonRepository {

    override fun signIn(userCredentials: UserCredentials): Either<UserData, RepositoryError> {
        var result: Either<UserData, RepositoryError>

        runBlocking {
            val state = remoteDataSource.login(
                email = userCredentials.email,
                pass = userCredentials.pass
            )

            result = when (state) {
                is State.Successful -> {
                    state.data.user?.let {
                        val userData = UserMapper.map(it)
                        eitherSuccess(userData)
                    } ?: eitherFailure(LogonRepositoryError.InvalidUserData)
                }
                is State.Failed -> {
                    val error = when (state.error) {
                        is FailureState.InvalidUser -> LogonRepositoryError.InvalidUser
                        is FailureState.InvalidUserData -> LogonRepositoryError.InvalidUserData
                        is FailureState.TooManyRequest -> LogonRepositoryError.Unknown
                        is FailureState.Unknown -> LogonRepositoryError.Unknown
                    }
                    eitherFailure(error)
                }
            }
        }

        return result
    }

    override fun signUp(userCredentials: UserCredentials): Either<UserData, RepositoryError> {
        var result: Either<UserData, RepositoryError>

        runBlocking {
            val state = remoteDataSource.createUser(
                email = userCredentials.email,
                pass = userCredentials.pass
            )

            result = when (state) {
                is State.Successful -> {
                    state.data.user?.let {
                        val userData = UserMapper.map(it)
                        eitherSuccess(userData)
                    } ?: eitherFailure(LogonRepositoryError.InvalidUserData)
                }
                is State.Failed -> {
                    eitherFailure(LogonRepositoryError.InvalidUserData)
                }
            }
        }

        return result
    }

    override fun restorePass(restorePass: EmailRestorePass): Either<Unit, RepositoryError> {
        var result: Either<Unit, RepositoryError>

        runBlocking {
            val state = remoteDataSource.rememberPassword(restorePass.email)

            result = when (state) {
                is State.Successful -> {
                    eitherSuccess(Unit)
                }
                is State.Failed -> {
                    eitherFailure(LogonRepositoryError.Unknown)
                }
            }
        }

        return result
    }

}