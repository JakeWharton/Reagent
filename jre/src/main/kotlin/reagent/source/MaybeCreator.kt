package reagent.source

import kotlinx.coroutines.experimental.CancellableContinuation
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import reagent.Maybe

interface MaybeCreator<out I> {
  fun subscribe(downstream: Downstream<I>)

  interface Downstream<in I> : Maybe.Observer<I> {
    val isDisposed: Boolean
  }
}

internal class MaybeFromCreator<out I>(private val creator: MaybeCreator<I>): Maybe<I>() {
  override suspend fun produce() = suspendCancellableCoroutine<I?> {
    creator.subscribe(DownstreamProducer(it))
  }

  class DownstreamProducer<in I>(
    private val continuation: CancellableContinuation<I?>
  ) : MaybeCreator.Downstream<I> {
    override val isDisposed get() = continuation.isCancelled

    override fun onItem(item: I) {
      continuation.resume(item)
    }

    override fun onNothing() {
      continuation.resume(null)
    }

    override fun onError(t: Throwable) {
      continuation.resumeWithException(t)
    }
  }
}
