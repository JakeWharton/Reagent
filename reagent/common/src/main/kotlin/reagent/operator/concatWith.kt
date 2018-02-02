package reagent.operator

import reagent.Observable
import reagent.source.concat

fun <I> Observable<I>.concatWith(other: Observable<I>) = concat(this, other)
