package com.jgpleo.domain_common.usecase.repository

data class RepositoryError<E>(
    val code: String = "No code",
    val message: String = "No message",
    val error: E? = null
)

data class DefaultRepositoryError(
    val code: String = "No code",
    val message: String = "No message"
)