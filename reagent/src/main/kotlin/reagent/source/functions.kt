@file:Suppress("NOTHING_TO_INLINE") // Public API convenience aliases.

package reagent.source

import reagent.Emitter
import reagent.Observable
import reagent.Task

fun <I> observable(body: suspend (emit: Emitter<I>) -> Unit): Observable<I> = ObservableFromSuspendingLambda(body)
fun <I> task(body: suspend () -> I): Task<I> = TaskFromSuspendingLambda(body)

fun emptyObservable(): Observable<Nothing> = ObservableEmpty
fun observableOf(): Observable<Nothing> = ObservableEmpty
fun <I> observableOf(item: I): Task<I> = TaskJust(item)
fun <I> observableOf(vararg items: I): Observable<I> = when (items.size) {
  0 -> ObservableEmpty
  1 -> TaskJust(items[0])
  else -> ObservableArray(items)
}

fun <I> observableReturning(func: () -> I): Task<I> = TaskFromLambda(func)

fun <I> deferObservable(func: () -> Observable<I>): Observable<I> = ObservableDeferred(func)
fun <I> deferTask(func: () -> Task<I>): Task<I> = TaskDeferred(func)

fun Throwable.toTask(): Task<Nothing> = TaskError(this)

fun <T> Array<T>.toObservable(): Observable<T> = ObservableArray(this)
fun <T> Iterable<T>.toObservable(): Observable<T> = ObservableIterable(this)
fun <T> Sequence<T>.toObservable(): Observable<T> = ObservableSequence(this)

expect fun interval(periodMillis: Int): Observable<Int>
expect fun timer(delayMillis: Int): Task<Unit>

inline fun <I> concat(first: Observable<I>, second: Observable<I>) = concat(listOf(first, second))
fun <I> concat(vararg observables: Observable<I>): Observable<I> = ObservableConcat(observables.toList())
fun <I> concat(observables: Iterable<Observable<I>>): Observable<I> = ObservableConcat(observables.toList())
