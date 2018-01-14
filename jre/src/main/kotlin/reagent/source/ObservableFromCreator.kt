package reagent.source

import kotlinx.coroutines.experimental.CancellableContinuation
import kotlinx.coroutines.experimental.CoroutineStart.UNDISPATCHED
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import reagent.Emitter
import reagent.Observable
import reagent.source.ObservableCreator.Downstream

internal class ObservableFromCreator<out I>(private val creator: ObservableCreator<I>): Observable<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    suspendCancellableCoroutine<Unit> {
      creator.subscribe(DownstreamEmitter(it, emit))
    }
  }

  class DownstreamEmitter<in I>(
    private val continuation: CancellableContinuation<Unit>,
    private val emit: Emitter<I>
  ) : Downstream<I> {
    override val isDisposed get() = continuation.isCancelled

    override fun onNext(item: I) {
      launch(Unconfined,
          UNDISPATCHED) {
        emit(item)
      }
    }

    override fun onComplete() {
      continuation.resume(Unit)
    }

    override fun onError(t: Throwable) {
      continuation.resumeWithException(t)
    }
  }
}
