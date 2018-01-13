package reagent.source

import reagent.Emitter
import reagent.Many

fun <I> emptyMany(): Many<I> = TaskComplete
fun <I> manyOf(item: I): Many<I> = OneJust(item)
fun <I> manyOf(vararg items: I): Many<I> = ManyFromArray(items)
fun <I> manyReturning(func: () -> I): Many<I> = OneFromLambda(func)
fun <I> manyRunning(func: () -> Unit): Many<I> = TaskFromLambda(func)

fun <I> deferMany(func: () -> Many<I>): Many<I> = ManyDeferred(func)

fun <I> Throwable.toMany(): Many<I> = OneError(this)
fun <T> Array<T>.toMany(): Many<T> = ManyFromArray(this)
fun <T> Iterable<T>.toMany(): Many<T> = ManyFromIterable(this)

internal class ManyFromArray<out I>(private val items: Array<out I>) : Many<I>() {
  override suspend fun subscribe(emitter: Emitter<I>) {
    items.forEach { emitter.send(it) }
  }
}

internal class ManyFromIterable<out I>(private val iterable: Iterable<I>): Many<I>() {
  override suspend fun subscribe(emitter: Emitter<I>) {
    iterable.forEach { emitter.send(it) }
  }
}

internal class ManyDeferred<out I>(private val func: () -> Many<I>): Many<I>() {
  override suspend fun subscribe(emitter: Emitter<I>) = func().subscribe(emitter)
}
