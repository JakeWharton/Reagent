package reagent.operator

import reagent.Maybe
import reagent.One
import reagent.Task

private val unitSupplier = { Unit }
private val noSuchElementSupplier = { throw NoSuchElementException("Maybe was empty.") }

fun Task.thenReturnUnit(): One<Unit> = OneFromTask(this, unitSupplier)
fun <I> Task.thenReturn(item: I): One<I> = OneFromTask(this, { item })
fun <I> Task.thenReturn(supplier: () -> I): One<I> = OneFromTask(this, supplier)

fun <I> Maybe<I>.item(): One<I> = OneFromMaybe(this, noSuchElementSupplier)
fun <I> Maybe<I>.itemOrElse(default: I): One<I> = OneFromMaybe(this, { default })
fun <I> Maybe<I>.itemOrElse(supplier: () -> I): One<I> = OneFromMaybe(this, supplier)

internal class OneFromTask<out I>(
  private val upstream: Task,
  private val supplier: () -> I
): One<I>() {
  override suspend fun produce(): I {
    upstream.run()
    return supplier()
  }
}

internal class OneFromMaybe<out I>(
  private val upstream: Maybe<I>,
  private val supplier: () -> I
): One<I>() {
  override suspend fun produce() = upstream.produce() ?: supplier()
}
