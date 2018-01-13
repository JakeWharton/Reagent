package reagent.source

import reagent.Maybe

fun <I> emptyMaybe(): Maybe<I> = TaskComplete
fun <I> maybeOf(item: I): Maybe<I> = OneJust(item)
fun <I> maybeReturning(func: () -> I): Maybe<I> = OneFromLambda(func)
fun <I> maybeRunning(func: () -> Unit): Maybe<I> = TaskFromLambda(func)

fun <I> deferMaybe(func: () -> Maybe<I>): Maybe<I> = MaybeDeferred(func)

fun <I> Throwable.toMaybe(): Maybe<I> = OneError(this)

internal class MaybeDeferred<out I>(private val func: () -> Maybe<I>): Maybe<I>() {
  override suspend fun produce() = func().produce()
}
