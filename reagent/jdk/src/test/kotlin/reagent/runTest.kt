package reagent

import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.runBlocking

actual fun runTest(block: suspend CoroutineScope.() -> Unit) = runBlocking {
  block()
}
