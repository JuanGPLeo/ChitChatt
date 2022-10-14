package com.jgpleo.chitchatt.logon.data.repository

import com.jgpleo.chitchatt.logon.data.mapper.UserMapper
import com.jgpleo.chitchatt.logon.data.source.RemoteDataSource
import com.jgpleo.chitchatt.logon.data.source.State
import com.jgpleo.chitchatt.logon.domain.error.DataError
import com.jgpleo.chitchatt.logon.domain.model.EmailRestorePass
import com.jgpleo.chitchatt.logon.domain.model.UserCredentials
import com.jgpleo.chitchatt.logon.domain.model.UserData
import com.jgpleo.chitchatt.logon.domain.repository.LogonRepository
import com.jgpleo.domain_common.usecase.repository.DefaultRepositoryError
import com.jgpleo.domain_common.usecase.repository.RepositoryError
import com.jgpleo.domain_common.usecase.result.Either
import com.jgpleo.domain_common.usecase.result.eitherFailure
import com.jgpleo.domain_common.usecase.result.eitherSuccess
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class LogonRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : LogonRepository {

    override fun signIn(userCredentials: UserCredentials): Either<UserData, RepositoryError<DataError>> {
        var result: Either<UserData, RepositoryError<DataError>>

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
                    } ?: eitherFailure(RepositoryError(message = "Unable to retrieve user data"))
                }
                is State.Failed -> {
                    eitherFailure(RepositoryError(message = state.message))
                }
            }
        }

        return result
    }

    override fun signUp(userCredentials: UserCredentials): Either<UserData, RepositoryError<DataError>> {
        var result: Either<UserData, RepositoryError<DataError>>

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
                    } ?: eitherFailure(RepositoryError(message = "Unable to retrieve user data"))
                }
                is State.Failed -> {
                    eitherFailure(RepositoryError(message = state.message))
                }
            }
        }

        return result
    }

    override fun restorePass(restorePass: EmailRestorePass): Either<Unit, DefaultRepositoryError> {
        var result: Either<Unit, DefaultRepositoryError>

        runBlocking {
            val state = remoteDataSource.rememberPassword(restorePass.email)

            result = when (state) {
                is State.Successful -> {
                    eitherSuccess(Unit)
                }
                is State.Failed -> {
                    eitherFailure(DefaultRepositoryError())
                }
            }
        }

        return result
    }

}