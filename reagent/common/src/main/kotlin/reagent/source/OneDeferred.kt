package reagent.source

import reagent.One

internal class OneDeferred<out I>(private val func: () -> One<I>) : One<I>() {
  override suspend fun produce() = func().produce()
}
