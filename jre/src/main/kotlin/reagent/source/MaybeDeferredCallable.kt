package reagent.source

import reagent.Maybe
import java.util.concurrent.Callable

internal class MaybeDeferredCallable<out I>(private val func: Callable<Maybe<I>>): Maybe<I>() {
  override suspend fun produce() = func.call().produce()
}
