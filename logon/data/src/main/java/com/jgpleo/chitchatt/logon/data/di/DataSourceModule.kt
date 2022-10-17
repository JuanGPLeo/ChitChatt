package com.jgpleo.chitchatt.logon.data.di

import com.jgpleo.chitchatt.logon.data.source.RemoteDataSource
import com.jgpleo.chitchatt.logon.data.source.remote.FirebaseRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideFirebaseRemoteDataSource(
        dataSource: FirebaseRemoteDataSource
    ): RemoteDataSource = dataSource
}