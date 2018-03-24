package reagent.source.test

import reagent.Observable
import reagent.source.emptyObservable
import reagent.source.toTask

fun <I> emptyActualObservable(): Observable<I> = emptyObservable()

fun <I> Throwable.toActualObservable(): Observable<I> = toTask()
