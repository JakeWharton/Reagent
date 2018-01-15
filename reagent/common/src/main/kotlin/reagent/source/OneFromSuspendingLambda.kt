package reagent.source

import reagent.One

internal class OneFromSuspendingLambda<out I>(private val body: suspend () -> I): One<I>() {
  override suspend fun produce() = body()
}
