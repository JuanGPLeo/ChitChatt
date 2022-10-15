package com.jgpleo.domain_common.usecase.result

sealed interface DomainSuccess {
    object EmptyResult: DomainSuccess
}