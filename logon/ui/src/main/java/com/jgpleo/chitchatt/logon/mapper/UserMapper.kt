package com.jgpleo.chitchatt.logon.mapper

import com.jgpleo.chitchatt.logon.domain.model.UserData
import com.jgpleo.chitchatt.logon.model.User

object UserMapper {

    fun map(data: UserData): User {
        return User(
            email = data.email,
            name = data.name
        )
    }

}