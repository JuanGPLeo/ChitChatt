package com.jgpleo.chitchatt.logon.domain.repository

import com.jgpleo.chitchatt.logon.domain.model.EmailRestorePass
import com.jgpleo.chitchatt.logon.domain.model.UserCredentials
import com.jgpleo.chitchatt.logon.domain.model.UserData
import com.jgpleo.domain_common.usecase.repository.RepositoryError
import com.jgpleo.domain_common.usecase.result.Either

interface LogonRepository {
    fun signIn(userCredentials: UserCredentials): Either<UserData, RepositoryError>
    fun signUp(userCredentials: UserCredentials): Either<UserData, RepositoryError>
    fun restorePass(restorePass: EmailRestorePass): Either<Unit, RepositoryError>
}