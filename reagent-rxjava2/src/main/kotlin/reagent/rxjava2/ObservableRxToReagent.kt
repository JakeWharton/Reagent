package reagent.rxjava2

import io.reactivex.disposables.Disposable
import kotlinx.coroutines.experimental.CoroutineStart.UNDISPATCHED
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import reagent.Emitter
import reagent.Observable
import io.reactivex.Observable as RxObservable
import io.reactivex.Observer as RxObserver

internal class ObservableRxToReagent<I>(private val upstream: RxObservable<I>) : Observable<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    suspendCancellableCoroutine<Unit> { continuation ->
      upstream.subscribe(object : RxObserver<I> {
        override fun onSubscribe(d: Disposable) {
          continuation.invokeOnCompletion {
            if (continuation.isCancelled) {
              d.dispose()
            }
          }
        }

        override fun onNext(item: I) {
          launch(Unconfined, UNDISPATCHED, parent = continuation) {
            emit(item)
          }
        }

        override fun onComplete() {
          continuation.resume(Unit)
        }

        override fun onError(e: Throwable) {
          continuation.resumeWithException(e)
        }
      })
    }
  }
}
