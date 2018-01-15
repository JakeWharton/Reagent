package reagent.source

import reagent.One
import kotlin.coroutines.experimental.suspendCoroutine
import kotlin.js.Promise

internal class OneFromPromise<out I>(private val promise: Promise<I>): One<I>() {
  override suspend fun produce() = suspendCoroutine<I> { continuation ->
    promise.then(continuation::resume, continuation::resumeWithException)
  }
}
