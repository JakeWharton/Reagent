package reagent.source

import kotlinx.coroutines.experimental.CancellableContinuation
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import reagent.One
import reagent.source.OneCreator.Downstream

internal class OneFromCreator<out I>(private val creator: OneCreator<I>): One<I>() {
  override suspend fun produce() = suspendCancellableCoroutine<I> {
    creator.subscribe(DownstreamProducer(it))
  }

  class DownstreamProducer<in I>(
    private val continuation: CancellableContinuation<I>
  ) : Downstream<I> {
    override val isDisposed get() = continuation.isCancelled

    override fun onItem(item: I) {
      continuation.resume(item)
    }

    override fun onError(t: Throwable) {
      continuation.resumeWithException(t)
    }
  }
}
