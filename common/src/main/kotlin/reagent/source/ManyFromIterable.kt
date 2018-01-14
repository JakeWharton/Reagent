package reagent.source

import reagent.Emitter
import reagent.Many

internal class ManyFromIterable<out I>(private val iterable: Iterable<I>): Many<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    iterable.forEach { emit(it) }
  }
}
