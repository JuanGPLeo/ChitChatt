package com.jgpleo.chitchatt.logon.domain.repository

import com.jgpleo.chitchatt.logon.domain.model.EmailRestorePass
import com.jgpleo.chitchatt.logon.domain.model.UserCredentials
import com.jgpleo.chitchatt.logon.domain.model.UserData
import com.jgpleo.domain_common.usecase.repository.DefaultRepositoryError
import com.jgpleo.domain_common.usecase.repository.RepositoryResult

interface LogonRepository {
    fun signIn(userCredentials: UserCredentials): RepositoryResult<UserData, DefaultRepositoryError>
    fun signUp(userCredentials: UserCredentials): RepositoryResult<UserData, DefaultRepositoryError>
    fun restorePass(restorePass: EmailRestorePass): RepositoryResult<Unit, DefaultRepositoryError>
}