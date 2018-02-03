package reagent

import kotlinx.coroutines.experimental.CoroutineStart.UNDISPATCHED
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.launch
import reagent.source.ObservableCreator
import reagent.source.ObservableArray
import reagent.source.ObservableFromCreator
import reagent.source.ObservableIterable
import reagent.source.ObservableDeferredCallable
import reagent.source.OneError
import reagent.source.OneFromCallable
import reagent.source.OneJust
import reagent.source.ObservableEmpty
import reagent.source.ObservableFromRunnable
import java.util.concurrent.Callable

/** Emits 0 to infinite items and then signals complete or error. */
actual abstract class Observable<out I> {
  actual abstract suspend fun subscribe(emit: Emitter<I>)

  interface Observer<in I> {
    fun onNext(item: I)
    fun onComplete()
    fun onError(t: Throwable)
  }

  fun subscribe(observer: Observer<I>): Disposable {
    val job = launch(Unconfined, UNDISPATCHED) {
      try {
        subscribe {
          observer.onNext(it)
          true // TODO hook up Disposable-like thing
        }
      } catch (t: Throwable) {
        observer.onError(t)
        return@launch
      }
      observer.onComplete()
    }
    return JobDisposable(job)
  }

  companion object {
    @JvmStatic fun <I> createObservable(body: ObservableCreator<I>): Observable<I> = ObservableFromCreator(body)
    @JvmStatic fun <I> empty(): Observable<I> = ObservableEmpty
    @JvmStatic fun <I> just(item: I): Observable<I> = OneJust(item)
    @JvmStatic fun <I> error(t: Throwable): Observable<I> = OneError(t)
    @JvmStatic fun <I> fromArray(vararg items: I): Observable<I> = ObservableArray(items)
    @JvmStatic fun <I> fromIterable(items: Iterable<I>): Observable<I> = ObservableIterable(items)
    @JvmStatic fun fromRunnable(runnable: Runnable): Observable<Nothing> = ObservableFromRunnable(runnable)
    @JvmStatic fun <I> fromCallable(callable: Callable<I>): Observable<I> = OneFromCallable(callable)
    @JvmStatic fun <I> deferObservable(callable: Callable<Observable<I>>): Observable<I> = ObservableDeferredCallable(callable)
  }
}
