package reagent.rxjava2

import io.reactivex.Observer
import io.reactivex.exceptions.CompositeException
import io.reactivex.exceptions.Exceptions
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import kotlinx.coroutines.experimental.CoroutineStart.LAZY
import kotlinx.coroutines.experimental.JobCancellationException
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.launch
import reagent.Observable

internal class ObservableReagentToRx<I : Any>(
  private val upstream: Observable<I>
): io.reactivex.Observable<I>() {
  override fun subscribeActual(observer: Observer<in I>) {
    val job = launch(Unconfined, LAZY) {
      try {
        upstream.subscribe {
          observer.onNext(it)
        }
      } catch (ignored: JobCancellationException) {
        return@launch
      } catch (t: Throwable) {
        Exceptions.throwIfFatal(t)
        try {
          observer.onError(t)
        } catch (inner: Throwable) {
          RxJavaPlugins.onError(CompositeException(t, inner))
        }
        return@launch
      }
      try {
        observer.onComplete()
      } catch (t: Throwable) {
        RxJavaPlugins.onError(UndeliverableException(t))
      }
    }
    observer.onSubscribe(JobDisposable(job))
    job.start()
  }
}
