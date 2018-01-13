package reagent

import kotlinx.coroutines.experimental.CoroutineStart.UNDISPATCHED
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.launch
import reagent.source.ManyDeferredCallable
import reagent.source.MaybeDeferredCallable
import reagent.source.OneError
import reagent.source.OneFromCallable
import reagent.source.OneJust
import reagent.source.TaskComplete
import reagent.source.TaskFromRunnable
import java.util.concurrent.Callable

/** Emits an item, signals nothing (no item), or signals error. */
actual abstract class Maybe<out I> : Many<I>() {
  actual abstract suspend fun produce(): I?

  actual override suspend fun subscribe(emitter: Emitter<I>) {
    produce()?.let { emitter.send(it) }
  }

  interface Observer<in I> {
    fun onItem(item: I)
    fun onNothing()
    fun onError(t: Throwable)
  }

  fun subscribe(observer: Observer<I>): Disposable {
    val job = launch(Unconfined, UNDISPATCHED) {
      val value = try {
        produce()
      } catch (t: Throwable) {
        observer.onError(t)
        return@launch
      }
      if (value != null) {
        observer.onItem(value)
      } else {
        observer.onNothing()
      }
    }
    return JobDisposable(job)
  }

  companion object {
    @JvmStatic fun <I> empty(): Maybe<I> = TaskComplete
    @JvmStatic fun <I> just(item: I): Maybe<I> = OneJust(item)
    @JvmStatic fun <I> error(t: Throwable): Maybe<I> = OneError(t)
    @JvmStatic fun <I> fromRunnable(runnable: Runnable): Maybe<I> = TaskFromRunnable(runnable)
    @JvmStatic fun <I> fromCallable(callable: Callable<I>): Maybe<I> = OneFromCallable(callable)
    @JvmStatic fun <I> deferMaybe(callable: Callable<Maybe<I>>): Maybe<I> = MaybeDeferredCallable(callable)
  }
}
