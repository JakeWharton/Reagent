@file:Suppress("NOTHING_TO_INLINE") // Public API convenience aliases.

package reagent.source

import reagent.Emitter
import reagent.Observable
import reagent.One

fun <I> observable(body: suspend (emit: Emitter<I>) -> Unit): Observable<I> = ObservableFromSuspendingLambda(body)
fun <I> one(body: suspend () -> I): One<I> = OneFromSuspendingLambda(body)

fun emptyObservable(): Observable<Nothing> = ObservableEmpty
fun observableOf(): Observable<Nothing> = ObservableEmpty
fun <I> observableOf(item: I): One<I> = OneJust(item)
fun <I> observableOf(vararg items: I): Observable<I> = when (items.size) {
  0 -> ObservableEmpty
  1 -> OneJust(items[0])
  else -> ObservableArray(items)
}

fun <I> observableReturning(func: () -> I): One<I> = OneFromLambda(func)

fun <I> deferObservable(func: () -> Observable<I>): Observable<I> = ObservableDeferred(func)
fun <I> deferOne(func: () -> One<I>): One<I> = OneDeferred(func)

fun Throwable.toOne(): One<Nothing> = OneError(this)

fun <T> Array<T>.toObservable(): Observable<T> = ObservableArray(this)
fun <T> Iterable<T>.toObservable(): Observable<T> = ObservableIterable(this)
fun <T> Sequence<T>.toObservable(): Observable<T> = ObservableSequence(this)

expect fun interval(periodMillis: Int): Observable<Int>
expect fun timer(delayMillis: Int): One<Unit>

inline fun <I> concat(first: Observable<I>, second: Observable<I>) = concat(listOf(first, second))
fun <I> concat(vararg observables: Observable<I>): Observable<I> = ObservableConcat(observables.toList())
fun <I> concat(observables: Iterable<Observable<I>>): Observable<I> = ObservableConcat(observables.toList())
