package reagent

import kotlinx.coroutines.experimental.CoroutineStart.UNDISPATCHED
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.launch
import reagent.source.TaskCreator
import reagent.source.TaskDeferredCallable
import reagent.source.TaskError
import reagent.source.TaskFromCallable
import reagent.source.TaskFromCreator
import reagent.source.TaskJust
import java.util.concurrent.Callable

/** Emits a single item or errors. */
actual abstract class Task<out I> : Observable<I>() {
  actual abstract suspend fun produce(): I

  actual override suspend fun subscribe(emit: Emitter<I>) {
    emit(produce())
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
    @JvmStatic fun <I> createOne(body: TaskCreator<I>): Task<I> = TaskFromCreator(body)
    @JvmStatic fun <I> just(item: I): Task<I> = TaskJust(item)
    @JvmStatic fun <I> error(t: Throwable): Task<I> = TaskError(t)
    @JvmStatic fun <I> fromCallable(callable: Callable<I>): Task<I> = TaskFromCallable(callable)
    @JvmStatic fun <I> deferOne(callable: Callable<Task<I>>): Task<I> = TaskDeferredCallable(callable)
  }
}
