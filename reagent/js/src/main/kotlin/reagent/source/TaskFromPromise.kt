package reagent.source

import reagent.Task
import kotlin.coroutines.experimental.suspendCoroutine
import kotlin.js.Promise

internal class TaskFromPromise<out I>(private val promise: Promise<I>): Task<I>() {
  override suspend fun produce() = suspendCoroutine<I> { continuation ->
    promise.then(continuation::resume, continuation::resumeWithException)
  }
}
