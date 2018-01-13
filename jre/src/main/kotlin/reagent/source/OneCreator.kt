package reagent.source

import kotlinx.coroutines.experimental.CancellableContinuation
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import reagent.One

interface OneCreator<out I> {
  fun subscribe(downstream: Downstream<I>)

  interface Downstream<in I> : One.Observer<I> {
    val isDisposed: Boolean
  }
}

internal class OneFromCreator<out I>(private val creator: OneCreator<I>): One<I>() {
  override suspend fun produce() = suspendCancellableCoroutine<I> {
    creator.subscribe(DownstreamProducer(it))
  }

  class DownstreamProducer<in I>(
    private val continuation: CancellableContinuation<I>
  ) : OneCreator.Downstream<I> {
    override val isDisposed get() = continuation.isCancelled

    override fun onItem(item: I) {
      continuation.resume(item)
    }

    override fun onError(t: Throwable) {
      continuation.resumeWithException(t)
    }
  }
}
