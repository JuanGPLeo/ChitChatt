package com.jgpleo.chitchatt.logon.data.source.remote.mapper

import com.google.firebase.auth.FirebaseUser
import com.jgpleo.chitchatt.logon.data.repository.model.UserEntity

object RemoteMapper {
    fun map(remoteData: FirebaseUser): UserEntity {
        return UserEntity(
            email = remoteData.email,
            userName = remoteData.displayName
        )
    }
}