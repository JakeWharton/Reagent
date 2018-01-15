package reagent.source

import reagent.Maybe

internal class MaybeFromSuspendingLambda<out I>(private val body: suspend () -> I?): Maybe<I>() {
  override suspend fun produce() = body()
}
