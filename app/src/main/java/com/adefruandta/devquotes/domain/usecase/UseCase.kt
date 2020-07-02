package com.adefruandta.devquotes.domain.usecase

import io.reactivex.Observable

abstract class UseCase<T> {

    abstract fun observe(): Observable<T>
}