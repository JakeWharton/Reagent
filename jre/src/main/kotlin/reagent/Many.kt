package reagent

import kotlinx.coroutines.experimental.CoroutineStart.UNDISPATCHED
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.launch
import reagent.source.ManyCreator
import reagent.source.ManyFromArray
import reagent.source.ManyFromCreator
import reagent.source.ManyFromIterable
import reagent.source.ManyDeferredCallable
import reagent.source.OneError
import reagent.source.OneFromCallable
import reagent.source.OneJust
import reagent.source.TaskComplete
import reagent.source.TaskFromRunnable
import java.util.concurrent.Callable

/** Emits 0 to infinite items and then signals complete or error. */
actual abstract class Many<out I> {
  actual abstract suspend fun subscribe(emitter: Emitter<I>)

  interface Observer<in I> {
    fun onNext(item: I)
    fun onComplete()
    fun onError(t: Throwable)
  }

  fun subscribe(observer: Observer<I>): Disposable {
    val job = launch(Unconfined, UNDISPATCHED) {
      try {
        subscribe(ObserverEmitter(observer))
      } catch (t: Throwable) {
        observer.onError(t)
        return@launch
      }
      observer.onComplete()
    }
    return JobDisposable(job)
  }

  internal class ObserverEmitter<in I>(private val observer: Observer<I>) : Emitter<I> {
    override suspend fun send(item: I) = observer.onNext(item)
  }

  companion object {
    @JvmStatic fun <I> createMany(body: ManyCreator<I>): Many<I> = ManyFromCreator(body)
    @JvmStatic fun <I> empty(): Many<I> = TaskComplete
    @JvmStatic fun <I> just(item: I): Many<I> = OneJust(item)
    @JvmStatic fun <I> error(t: Throwable): Many<I> = OneError(t)
    @JvmStatic fun <I> fromArray(vararg items: I): Many<I> = ManyFromArray(items)
    @JvmStatic fun <I> fromIterable(items: Iterable<I>): Many<I> = ManyFromIterable(items)
    @JvmStatic fun <I> fromRunnable(runnable: Runnable): Many<I> = TaskFromRunnable(runnable)
    @JvmStatic fun <I> fromCallable(callable: Callable<I>): Many<I> = OneFromCallable(callable)
    @JvmStatic fun <I> deferMany(callable: Callable<Many<I>>): Many<I> = ManyDeferredCallable(callable)
  }
}
