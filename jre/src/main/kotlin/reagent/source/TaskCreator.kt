package reagent.source

import kotlinx.coroutines.experimental.CancellableContinuation
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import reagent.Task

interface TaskCreator {
  fun subscribe(downstream: Downstream)

  interface Downstream : Task.Observer {
    val isDisposed: Boolean
  }
}

internal class TaskFromCreator(private val creator: TaskCreator): Task() {
  override suspend fun run() = suspendCancellableCoroutine<Unit> {
    creator.subscribe(DownstreamProducer(it))
  }

  class DownstreamProducer(
    private val continuation: CancellableContinuation<Unit>
  ) : TaskCreator.Downstream {
    override val isDisposed get() = continuation.isCancelled

    override fun onComplete() {
      continuation.resume(Unit)
    }

    override fun onError(t: Throwable) {
      continuation.resumeWithException(t)
    }
  }
}
