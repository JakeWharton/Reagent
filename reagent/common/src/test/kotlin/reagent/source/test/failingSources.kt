package reagent.source.test

import reagent.Observable
import reagent.Maybe
import reagent.One
import reagent.Task
import kotlin.test.fail

fun <T> failMany(): Observable<T> = fail()
fun <T> failMaybe(): Maybe<T> = fail()
fun <T> failOne(): One<T> = fail()
fun failTask(): Task = fail()
