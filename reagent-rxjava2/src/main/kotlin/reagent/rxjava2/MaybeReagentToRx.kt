package reagent.rxjava2

import io.reactivex.MaybeObserver
import io.reactivex.exceptions.CompositeException
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import kotlinx.coroutines.experimental.CoroutineStart.LAZY
import kotlinx.coroutines.experimental.JobCancellationException
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.launch
import reagent.Maybe

internal class MaybeReagentToRx<I : Any>(
  private val upstream: Maybe<I>
) : io.reactivex.Maybe<I>() {
  override fun subscribeActual(observer: MaybeObserver<in I>) {
    val job = launch(Unconfined, LAZY) {
      val result = try {
        upstream.produce()
      } catch (ignored: JobCancellationException) {
        return@launch
      } catch (t: Throwable) {
        try {
          observer.onError(t)
        } catch (inner: Throwable) {
          RxJavaPlugins.onError(CompositeException(t, inner))
        }
        return@launch
      }
      try {
        if (result != null) {
          observer.onSuccess(result)
        } else {
          observer.onComplete()
        }
      } catch (t: Throwable) {
        RxJavaPlugins.onError(UndeliverableException(t))
      }
    }
    observer.onSubscribe(JobDisposable(job))
    job.start()
  }
}
