package reagent.source

import reagent.One

internal class OneJust<out I>(private val item: I) : One<I>() {
  override suspend fun produce() = item
}
