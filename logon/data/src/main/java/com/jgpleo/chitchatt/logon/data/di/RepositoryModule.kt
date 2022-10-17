package com.jgpleo.chitchatt.logon.data.di

import com.jgpleo.chitchatt.logon.data.repository.LogonRepositoryImp
import com.jgpleo.chitchatt.logon.domain.repository.LogonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideLogonRepository(
        repository: LogonRepositoryImp
    ): LogonRepository = repository

}