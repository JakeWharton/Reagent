package reagent.source

import reagent.One

internal class OneError<out I>(private val t: Throwable) : One<I>() {
  override suspend fun produce() = throw t
}
