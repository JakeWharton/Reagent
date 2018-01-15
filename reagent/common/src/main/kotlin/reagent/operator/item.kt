package reagent.operator

import reagent.Maybe
import reagent.One

private val noSuchElementSupplier = { throw NoSuchElementException("Maybe was empty.") }

fun <I> Maybe<I>.item(): One<I> = MaybeItem(this, noSuchElementSupplier)
fun <I> Maybe<I>.itemOrElse(default: I): One<I> = MaybeItem(this, { default })
fun <I> Maybe<I>.itemOrElse(supplier: () -> I): One<I> = MaybeItem(this, supplier)

internal class MaybeItem<out I>(
  private val upstream: Maybe<I>,
  private val supplier: () -> I
): One<I>() {
  override suspend fun produce() = upstream.produce() ?: supplier()
}
