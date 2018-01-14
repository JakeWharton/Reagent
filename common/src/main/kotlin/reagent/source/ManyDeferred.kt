package reagent.source

import reagent.Emitter
import reagent.Many

internal class ManyDeferred<out I>(private val func: () -> Many<I>): Many<I>() {
  override suspend fun subscribe(emit: Emitter<I>) = func().subscribe(emit)
}
