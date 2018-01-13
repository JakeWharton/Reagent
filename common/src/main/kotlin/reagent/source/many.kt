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
fun <T> Sequence<T>.toMany(): Many<T> = ManyFromSequence(this)

fun IntProgression.toMany(): Many<Int> = ManyFromIntProgression(this)
fun LongProgression.toMany(): Many<Long> = ManyFromLongProgression(this)
fun CharProgression.toMany(): Many<Char> = ManyFromCharProgression(this)

internal class ManyFromArray<out I>(private val items: Array<out I>) : Many<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    items.forEach { emit(it) }
  }
}

internal class ManyFromIterable<out I>(private val iterable: Iterable<I>): Many<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    iterable.forEach { emit(it) }
  }
}

internal class ManyFromSequence<out I>(private val iterable: Sequence<I>): Many<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    iterable.forEach { emit(it) }
  }
}

internal class ManyDeferred<out I>(private val func: () -> Many<I>): Many<I>() {
  override suspend fun subscribe(emit: Emitter<I>) = func().subscribe(emit)
}

internal class ManyFromIntProgression(private val progression: IntProgression): Many<Int>() {
  override suspend fun subscribe(emit: Emitter<Int>) {
    for (value in progression) {
      emit(value)
    }
  }
}

internal class ManyFromLongProgression(private val progression: LongProgression): Many<Long>() {
  override suspend fun subscribe(emit: Emitter<Long>) {
    for (value in progression) {
      emit(value)
    }
  }
}

internal class ManyFromCharProgression(private val progression: CharProgression): Many<Char>() {
  override suspend fun subscribe(emit: Emitter<Char>) {
    for (value in progression) {
      emit(value)
    }
  }
}
