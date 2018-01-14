package reagent.source

import reagent.One

internal class OneFromLambda<out I>(private val func: () -> I) : One<I>() {
  override suspend fun produce() = func()
}
