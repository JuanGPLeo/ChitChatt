package com.jgpleo.chitchatt.logon.data.mapper

import com.jgpleo.chitchatt.logon.data.repository.model.UserEntity
import com.jgpleo.chitchatt.logon.domain.model.UserData

object UserMapper {

    fun map(entity: UserEntity): UserData {
        return UserData(
            email = entity.email ?: "",
            name = entity.userName ?: ""
        )
    }

}