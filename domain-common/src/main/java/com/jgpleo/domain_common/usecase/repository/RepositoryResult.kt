package com.jgpleo.domain_common.usecase.repository

import com.jgpleo.domain_common.usecase.result.Either

typealias RepositoryResult<T, E> = Either<T, RepositoryError<E>>