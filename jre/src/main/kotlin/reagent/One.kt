package reagent

import kotlinx.coroutines.experimental.CoroutineStart.UNDISPATCHED
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.launch
import reagent.source.OneDeferredCallable
import reagent.source.OneError
import reagent.source.OneFromCallable
import reagent.source.OneJust
import java.util.concurrent.Callable

/** Emits a single item or errors. */
actual abstract class One<out I> : Maybe<I>() {
  actual abstract override suspend fun produce(): I

  actual override suspend fun subscribe(emitter: Emitter<I>) {
    emitter.send(produce())
  }

  interface Observer<in I> {
    fun onItem(item: I)
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
      observer.onItem(value)
    }
    return JobDisposable(job)
  }

  companion object {
    @JvmStatic fun <I> just(item: I): One<I> = OneJust(item)
    @JvmStatic fun <I> error(t: Throwable): One<I> = OneError(t)
    @JvmStatic fun <I> fromCallable(callable: Callable<I>): One<I> = OneFromCallable(callable)
    @JvmStatic fun <I> deferOne(callable: Callable<One<I>>): One<I> = OneDeferredCallable(callable)
  }
}
