package reagent.source

import reagent.One

fun <I> oneOf(item: I): One<I> = OneJust(item)
fun <I> oneReturning(func: () -> I): One<I> = OneFromLambda(func)

fun <I> deferOne(func: () -> One<I>): One<I> = OneDeferred(func)

fun <I> Throwable.toOne(): One<I> = OneError(this)

internal class OneError<out I>(private val t: Throwable) : One<I>() {
  override suspend fun produce() = throw t
}

internal class OneFromLambda<out I>(private val func: () -> I) : One<I>() {
  override suspend fun produce() = func()
}

internal class OneJust<out I>(private val item: I) : One<I>() {
  override suspend fun produce() = item
}

internal class OneDeferred<out I>(private val func: () -> One<I>) : One<I>() {
  override suspend fun produce() = func().produce()
}
