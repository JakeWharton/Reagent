package reagent.source

import reagent.One
import java.util.concurrent.Callable

internal class OneFromCallable<out I>(private val func: Callable<I>) : One<I>() {
  override suspend fun produce() = func.call()
}
