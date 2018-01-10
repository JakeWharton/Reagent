package reagent

import kotlinx.coroutines.experimental.CoroutineScope

expect fun <T> runBlocking(block: suspend CoroutineScope.() -> T)
