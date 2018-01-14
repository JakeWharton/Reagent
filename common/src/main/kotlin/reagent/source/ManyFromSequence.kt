package reagent.source

import reagent.Emitter
import reagent.Many

internal class ManyFromSequence<out I>(private val iterable: Sequence<I>): Many<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    iterable.forEach { emit(it) }
  }
}
