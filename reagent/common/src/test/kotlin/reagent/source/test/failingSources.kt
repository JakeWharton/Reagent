package reagent.source.test

import reagent.Observable
import reagent.One
import kotlin.test.fail

fun <T> failObservable(): Observable<T> = fail()
fun <T> failOne(): One<T> = fail()
