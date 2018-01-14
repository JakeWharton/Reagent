package reagent.source.test

import reagent.Observable
import reagent.Maybe
import reagent.source.emptyObservable
import reagent.source.observableOf
import reagent.source.toTask

fun <I> emptyMany(): Observable<I> = emptyObservable()
fun <I> emptyMaybe(): Maybe<I> = emptyObservable()

fun <I> maybeOf(item: I): Maybe<I> = observableOf(item)

fun <I> Throwable.toMany(): Observable<I> = toTask()
fun <I> Throwable.toMaybe(): Maybe<I> = toTask()
