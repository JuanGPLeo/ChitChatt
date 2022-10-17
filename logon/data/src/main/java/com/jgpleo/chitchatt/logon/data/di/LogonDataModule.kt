package com.jgpleo.chitchatt.logon.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        DataSourceModule::class,
        RepositoryModule::class
    ]
)
@InstallIn(SingletonComponent::class)
interface LogonDataModule