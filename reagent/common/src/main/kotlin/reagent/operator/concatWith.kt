package reagent.operator

import reagent.Observable
import reagent.Task
import reagent.source.concat

fun <I> Observable<I>.concatWith(other: Observable<I>) = concat(this, other)
fun Task.concatWith(other: Task) = concat(this, other)
