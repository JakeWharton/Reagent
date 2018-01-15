package reagent.source

import reagent.Maybe

internal class MaybeDeferred<out I>(private val func: () -> Maybe<I>): Maybe<I>() {
  override suspend fun produce() = func().produce()
}
