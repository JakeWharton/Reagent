package reagent

import kotlinx.coroutines.experimental.CoroutineScope

actual fun <T> runBlocking(block: suspend CoroutineScope.() -> T) {
  kotlinx.coroutines.experimental.runBlocking {
    block()
  }
}
