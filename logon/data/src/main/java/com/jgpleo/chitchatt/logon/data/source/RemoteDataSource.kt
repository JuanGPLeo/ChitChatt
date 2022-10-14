package com.jgpleo.chitchatt.logon.data.source

import com.jgpleo.chitchatt.logon.data.repository.model.UserEntity

interface RemoteDataSource {
    suspend fun createUser(email: String, pass: String): State<Response>
    suspend fun login(email: String, pass: String): State<Response>
    suspend fun rememberPassword(email: String): State<Unit>
}

data class Response(
    val user: UserEntity? = null
)

sealed class State<T> {
    data class Successful<T>(val data: T) : State<T>()
    data class Failed<T>(val message: String) : State<T>()

    companion object {
        fun <T> success(data: T) = Successful(data)
        fun <T> failure(message: String) = Failed<T>(message)
    }
}