package reagent.source

import reagent.Emitter
import reagent.Many
import reagent.Maybe
import reagent.One
import reagent.Task

fun <I> many(body: suspend (emit: Emitter<I>) -> Unit): Many<I> = ManyFromSuspendingLambda(body)
fun <I> maybe(body: suspend () -> I?): Maybe<I> = MaybeFromSuspendingLambda(body)
fun <I> one(body: suspend () -> I): One<I> = OneFromSuspendingLambda(body)
fun task(body: suspend () -> Unit): Task = TaskFromSuspendingLambda(body)

fun <I> emptyMany(): Many<I> = TaskComplete
fun <I> emptyMaybe(): Maybe<I> = TaskComplete
fun emptyTask(): Task = TaskComplete

fun <I> manyOf(item: I): Many<I> = OneJust(item)
fun <I> manyOf(vararg items: I): Many<I> = when (items.size) {
  0 -> TaskComplete
  1 -> OneJust(items[0])
  else -> ManyFromArray(items)
}
fun <I> maybeOf(item: I): Maybe<I> = OneJust(item)
fun <I> oneOf(item: I): One<I> = OneJust(item)

fun <I> manyReturning(func: () -> I): Many<I> = OneFromLambda(func)
fun <I> maybeReturning(func: () -> I): Maybe<I> = OneFromLambda(func)
fun <I> oneReturning(func: () -> I): One<I> = OneFromLambda(func)

fun <I> manyRunning(func: () -> Unit): Many<I> = TaskFromLambda(func)
fun <I> maybeRunning(func: () -> Unit): Maybe<I> = TaskFromLambda(func)
fun taskRunning(func: () -> Unit): Task = TaskFromLambda(func)

fun <I> deferMany(func: () -> Many<I>): Many<I> = ManyDeferred(func)
fun <I> deferMaybe(func: () -> Maybe<I>): Maybe<I> = MaybeDeferred(func)
fun <I> deferOne(func: () -> One<I>): One<I> = OneDeferred(func)
fun deferTask(func: () -> Task): Task = TaskDeferred(func)

fun <I> Throwable.toMany(): Many<I> = OneError(this)
fun <I> Throwable.toMaybe(): Maybe<I> = OneError(this)
fun <I> Throwable.toOne(): One<I> = OneError(this)
fun Throwable.toTask(): Task = TaskError(this)

fun <T> Array<T>.toMany(): Many<T> = ManyFromArray(this)
fun <T> Iterable<T>.toMany(): Many<T> = ManyFromIterable(this)
fun <T> Sequence<T>.toMany(): Many<T> = ManyFromSequence(this)

fun IntProgression.toMany(): Many<Int> = ManyFromIntProgression(this)
fun LongProgression.toMany(): Many<Long> = ManyFromLongProgression(this)
fun CharProgression.toMany(): Many<Char> = ManyFromCharProgression(this)

expect fun interval(periodMillis: Int): Many<Int>
expect fun timer(delayMillis: Int): One<Unit>
