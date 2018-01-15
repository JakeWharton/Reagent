@file:Suppress("NOTHING_TO_INLINE") // Public API convenience aliases.

package reagent.source

import reagent.Emitter
import reagent.Observable
import reagent.Maybe
import reagent.One
import reagent.Task

fun <I> observable(body: suspend (emit: Emitter<I>) -> Unit): Observable<I> = ObservableFromSuspendingLambda(body)
fun <I> maybe(body: suspend () -> I?): Maybe<I> = MaybeFromSuspendingLambda(body)
fun <I> one(body: suspend () -> I): One<I> = OneFromSuspendingLambda(body)
fun task(body: suspend () -> Unit): Task = TaskFromSuspendingLambda(body)

fun emptyObservable(): Task = TaskComplete
fun observableOf(): Task = TaskComplete
fun <I> observableOf(item: I): One<I> = OneJust(item)
fun <I> observableOf(vararg items: I): Observable<I> = when (items.size) {
  0 -> TaskComplete
  1 -> OneJust(items[0])
  else -> ObservableArray(items)
}

fun <I> observableReturning(func: () -> I): One<I> = OneFromLambda(func)
fun observableRunning(func: () -> Unit): Task = TaskFromLambda(func)

fun <I> deferMany(func: () -> Observable<I>): Observable<I> = ObservableDeferred(func)
fun <I> deferMaybe(func: () -> Maybe<I>): Maybe<I> = MaybeDeferred(func)
fun <I> deferOne(func: () -> One<I>): One<I> = OneDeferred(func)
fun deferTask(func: () -> Task): Task = TaskDeferred(func)

fun <I> Throwable.toOne(): One<I> = OneError(this)
fun Throwable.toTask(): Task = TaskError(this)

fun <T> Array<T>.toObservable(): Observable<T> = ObservableArray(this)
fun <T> Iterable<T>.toObservable(): Observable<T> = ObservableIterable(this)
fun <T> Sequence<T>.toObservable(): Observable<T> = ObservableSequence(this)

fun IntProgression.toObservable(): Observable<Int> = ObservableIntProgression(this)
fun LongProgression.toObservable(): Observable<Long> = ObservableLongProgression(this)
fun CharProgression.toObservable(): Observable<Char> = ObservableCharProgression(this)

expect fun interval(periodMillis: Int): Observable<Int>
expect fun timer(delayMillis: Int): One<Unit>

inline fun <I> concat(first: Observable<I>, second: Observable<I>) = concat(listOf(first, second))
fun <I> concat(vararg observables: Observable<I>): Observable<I> = ObservableConcat(observables.toList())
fun <I> concat(observables: Iterable<Observable<I>>): Observable<I> = ObservableConcat(observables.toList())
inline fun concat(first: Task, second: Task) = concat(listOf(first, second))
fun concat(vararg tasks: Task): Task = TaskConcat(tasks.toList())
fun concat(tasks: Iterable<Task>): Task = TaskConcat(tasks.toList())
