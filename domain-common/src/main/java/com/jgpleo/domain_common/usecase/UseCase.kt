package com.jgpleo.domain_common.usecase

import com.jgpleo.domain_common.usecase.dispatcher.DispatcherProvider
import com.jgpleo.domain_common.usecase.result.DomainError
import com.jgpleo.domain_common.usecase.result.DomainSuccess
import com.jgpleo.domain_common.usecase.result.Either
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

abstract class UseCase<T>(
    protected open val dispatcherProvider: DispatcherProvider
) {

    protected open fun dispatcher(): CoroutineContext = dispatcherProvider.io()

    fun prepare(input: T) = prepareFlow(input).flowOn(dispatcher())

    protected abstract fun prepareFlow(input: T): Flow<Either<DomainSuccess, DomainError>>

}