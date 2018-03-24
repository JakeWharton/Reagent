package reagent.source.test

import reagent.Observable
import reagent.Task
import kotlin.test.fail

fun <T> failObservable(): Observable<T> = fail()
fun <T> failOne(): Task<T> = fail()
