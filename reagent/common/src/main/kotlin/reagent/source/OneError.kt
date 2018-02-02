package reagent.source

import reagent.One

internal class OneError(private val t: Throwable) : One<Nothing>() {
  override suspend fun produce() = throw t
}
