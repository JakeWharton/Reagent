package reagent.source.test

import reagent.Observable
import reagent.source.emptyObservable
import reagent.source.toOne

fun <I> emptyActualObservable(): Observable<I> = emptyObservable()

fun <I> Throwable.toActualObservable(): Observable<I> = toOne()
