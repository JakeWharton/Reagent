package reagent

import kotlinx.coroutines.experimental.CoroutineStart.UNDISPATCHED
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.launch
import reagent.source.TaskComplete
import reagent.source.TaskCreator
import reagent.source.TaskDeferredCallable
import reagent.source.TaskError
import reagent.source.TaskFromCallable
import reagent.source.TaskFromCreator
import reagent.source.TaskFromRunnable
import java.util.concurrent.Callable

/** Signals complete or error. Has no items. */
actual abstract class Task : Maybe<Nothing>() {
  actual abstract suspend fun run()

  actual override suspend fun produce(): Nothing? {
    run()
    return null
  }

  actual override suspend fun subscribe(emitter: Emitter<Nothing>) = run()

  interface Observer {
    fun onComplete()
    fun onError(t: Throwable)
  }

  fun subscribe(observer: Observer): Disposable {
    val job = launch(Unconfined, UNDISPATCHED) {
      try {
        run()
      } catch (t: Throwable) {
        observer.onError(t)
        return@launch
      }
      observer.onComplete()
    }
    return JobDisposable(job)
  }

  companion object {
    @JvmStatic fun createTask(body: TaskCreator): Task = TaskFromCreator(body)
    @JvmStatic fun empty(): Task = TaskComplete
    @JvmStatic fun error(t: Throwable): Task = TaskError(t)
    @JvmStatic fun fromRunnable(runnable: Runnable): Task = TaskFromRunnable(runnable)
    @JvmStatic fun fromCallable(callable: Callable<Any>): Task = TaskFromCallable(callable)
    @JvmStatic fun deferTask(callable: Callable<Task>): Task = TaskDeferredCallable(callable)
  }
}
