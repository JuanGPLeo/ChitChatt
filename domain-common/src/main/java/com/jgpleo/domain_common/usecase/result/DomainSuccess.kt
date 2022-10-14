package com.jgpleo.domain_common.usecase.result

sealed interface DomainSuccess {
    object EmptyResult: DomainSuccess
    data class Result<out T>(val data: T) : DomainSuccess
}