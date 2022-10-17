package com.jgpleo.chitchatt.di

import com.jgpleo.chitchatt.logon.data.di.LogonDataModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        LogonDataModule::class
    ]
)
@InstallIn(SingletonComponent::class)
interface FeatureModules