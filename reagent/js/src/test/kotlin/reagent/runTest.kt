package reagent

import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.promise

actual fun runTest(block: suspend CoroutineScope.() -> Unit) = promise { block() }.asDynamic()
