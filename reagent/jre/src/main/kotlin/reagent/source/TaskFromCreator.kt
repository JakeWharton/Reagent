package reagent.source

import kotlinx.coroutines.experimental.CancellableContinuation
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import reagent.Task
import reagent.source.TaskCreator.Downstream

internal class TaskFromCreator(private val creator: TaskCreator): Task() {
  override suspend fun run() = suspendCancellableCoroutine<Unit> {
    creator.subscribe(DownstreamProducer(it))
  }

  class DownstreamProducer(
    private val continuation: CancellableContinuation<Unit>
  ) : Downstream {
    override val isDisposed get() = continuation.isCancelled

    override fun onComplete() {
      continuation.resume(Unit)
    }

    override fun onError(t: Throwable) {
      continuation.resumeWithException(t)
    }
  }
}
