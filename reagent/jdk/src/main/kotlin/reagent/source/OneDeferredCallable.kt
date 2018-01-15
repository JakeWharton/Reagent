package reagent.source

import reagent.One
import java.util.concurrent.Callable

internal class OneDeferredCallable<out I>(private val func: Callable<One<I>>): One<I>() {
  override suspend fun produce() = func.call().produce()
}
