package reagent

import kotlinx.coroutines.experimental.CoroutineScope

expect fun runTest(block: suspend CoroutineScope.() -> Unit)
