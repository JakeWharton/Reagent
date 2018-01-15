package reagent.rxjava2

import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.exceptions.CompositeException
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import kotlinx.coroutines.experimental.CoroutineStart.LAZY
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.launch
import reagent.Task

internal class TaskReagentToRx(
  private val upstream: Task
) : Completable() {
  override fun subscribeActual(observer: CompletableObserver) {
    val job = launch(Unconfined, LAZY) {
      try {
        upstream.run()
      } catch (t: Throwable) {
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
