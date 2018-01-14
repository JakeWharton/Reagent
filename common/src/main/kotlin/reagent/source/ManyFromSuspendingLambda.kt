package reagent.source

import reagent.Emitter
import reagent.Many

internal class ManyFromSuspendingLambda<out I>(
  private val body: suspend (emit: Emitter<I>) -> Unit
): Many<I>() {
  override suspend fun subscribe(emit: Emitter<I>) = body(emit)
}
