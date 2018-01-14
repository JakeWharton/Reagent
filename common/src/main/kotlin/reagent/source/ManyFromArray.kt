package reagent.source

import reagent.Emitter
import reagent.Many

internal class ManyFromArray<out I>(private val items: Array<out I>) : Many<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    items.forEach { emit(it) }
  }
}
