package reagent.rxjava2

import io.reactivex.disposables.Disposable
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import reagent.One
import io.reactivex.Maybe as RxMaybe
import io.reactivex.MaybeObserver as RxMaybeObserver

internal class MaybeRxToReagent<I>(private val upstream: RxMaybe<I>) : One<I?>() {
  override suspend fun produce() = suspendCancellableCoroutine<I?> { continuation ->
    upstream.subscribe(object : RxMaybeObserver<I> {
      override fun onSubscribe(d: Disposable) {
        continuation.invokeOnCompletion {
          if (continuation.isCancelled) {
            d.dispose()
          }
        }
      }

      override fun onSuccess(item: I) {
        continuation.resume(item)
      }

      override fun onComplete() {
        continuation.resume(null)
      }

      override fun onError(e: Throwable) {
        continuation.resumeWithException(e)
      }
    })
  }
}
