package reagent.source

import reagent.Emitter
import reagent.Many
import java.util.concurrent.Callable

internal class ManyDeferredCallable<out I>(private val func: Callable<Many<I>>): Many<I>() {
  override suspend fun subscribe(emit: Emitter<I>) = func.call().subscribe(emit)
}
